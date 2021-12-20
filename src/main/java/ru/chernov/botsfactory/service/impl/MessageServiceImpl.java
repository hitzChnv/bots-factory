package ru.chernov.botsfactory.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboard;
import ru.chernov.botsfactory.model.entity.Chat;
import ru.chernov.botsfactory.model.enums.InlineKeyboardType;
import ru.chernov.botsfactory.model.enums.ReplyKeyboardType;
import ru.chernov.botsfactory.model.keyboards.buttons.InlineButton;
import ru.chernov.botsfactory.model.keyboards.buttons.ReplyButton;
import ru.chernov.botsfactory.service.ChatService;
import ru.chernov.botsfactory.service.InlineKeyboardService;
import ru.chernov.botsfactory.service.MessageService;
import ru.chernov.botsfactory.service.ReplyKeyboardService;

import java.util.Optional;

import static java.util.Objects.nonNull;

@Service
@RequiredArgsConstructor
public class MessageServiceImpl implements MessageService {

    private final ReplyKeyboardService replyService;
    private final InlineKeyboardService inlineService;
    private final ChatService chatService;

    @Override
    public SendMessage create(Message message) {
        var chatId = message.getChatId().toString();

        var optionalChat = chatService.findById(chatId);
        var chat = optionalChat.isEmpty() ? chatService.create(chatId) : optionalChat.get();

        var inlineButton = findInlineButtonByCommand(message, chat);
        var replyButton = findReplyButtonByCommand(message, chat);

        if (replyButton.isEmpty() && inlineButton.isPresent()) {
            return createByCommand(chatId, inlineButton.get());

        } else if (replyButton.isPresent() && inlineButton.isEmpty()) {
            return createByCommand(chatId, replyButton.get());

        } else {
            return buildDefaultMessage(message);

        }
    }

    private Optional<InlineButton> findInlineButtonByCommand(Message message, Chat chat) {
        var inlineKeyboards = inlineService.findAllByChatId(chat.getId());

        return inlineKeyboards.stream()
                .flatMap(k -> k.getRows().stream())
                .flatMap(r -> r.getButtons().stream())
                .filter(b -> b.getText().equals(message.getText()))
                .findFirst();
    }

    private Optional<ReplyButton> findReplyButtonByCommand(Message message, Chat chat) {
        var replyKeyboards = replyService.findAllByChatId(chat.getId());

        return replyKeyboards.stream()
                .flatMap(k -> k.getRows().stream())
                .flatMap(r -> r.getButtons().stream())
                .filter(b -> b.getText().equals(message.getText()))
                .findFirst();
    }

    private SendMessage createByCommand(String chatId, InlineButton button) {
        var type = button.getNextKeyboardType();
        var text = button.getNextMessageText();

        if (nonNull(type)) {
            var keyboard = InlineKeyboardType.hasType(type)
                    ? inlineService.build(InlineKeyboardType.valueOf(type))
                    : replyService.build(ReplyKeyboardType.valueOf(type));

            return buildMessage(chatId, text, keyboard);
        }

        return buildMessage(chatId, text);

    }

    private SendMessage createByCommand(String chatId, ReplyButton button) {
        var type = button.getNextKeyboardType();
        var text = button.getNextMessageText();

        if (nonNull(type)) {
            var keyboard = ReplyKeyboardType.hasType(type)
                    ? replyService.build(ReplyKeyboardType.valueOf(type))
                    : inlineService.build(InlineKeyboardType.valueOf(type));

            return buildMessage(chatId, text, keyboard);
        }

        return buildMessage(chatId, text);
    }

    private SendMessage buildMessage(String chatId, String text) {
        return SendMessage.builder()
                .chatId(chatId)
                .text(text)
                .build();
    }


    private SendMessage buildMessage(String chatId, String text, ReplyKeyboard keyboard) {
        return SendMessage.builder()
                .chatId(chatId)
                .text(text)
                .replyMarkup(keyboard)
                .build();
    }

    private SendMessage buildDefaultMessage(Message message) {
        return SendMessage.builder()
                .chatId(message.getChatId().toString())
                .text(message.getText())
                .replyMarkup(replyService.build(ReplyKeyboardType.DEFAULT_REPLY_KEYBOARD))
                .build();
    }
}
