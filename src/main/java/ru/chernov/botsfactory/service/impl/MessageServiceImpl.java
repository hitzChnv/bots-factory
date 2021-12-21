package ru.chernov.botsfactory.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboard;
import ru.chernov.botsfactory.model.entity.Chat;
import ru.chernov.botsfactory.service.ChatService;
import ru.chernov.botsfactory.service.KeyboardService;
import ru.chernov.botsfactory.service.MessageService;

import java.util.Optional;

import static java.util.Objects.nonNull;
import static ru.chernov.botsfactory.converter.KeyboardConverter.convert;
import static ru.chernov.botsfactory.model.enums.KeyboardType.DEFAULT_REPLY_KEYBOARD;

@Service
@RequiredArgsConstructor
public class MessageServiceImpl implements MessageService {

    private final KeyboardService keyboardService;
    private final ChatService chatService;

    @Override
    @Transactional
    public SendMessage create(Message message) {
        var chatId = message.getChatId().toString();

        var optionalChat = chatService.findById(chatId);
        var chat = optionalChat.isEmpty() ? chatService.create(chatId) : optionalChat.get();

        return create(message, chat);
    }

    private SendMessage create(Message message, Chat chat) {
        var defaultKeyboard = convert(keyboardService.findByType(DEFAULT_REPLY_KEYBOARD));

        return buildMessage(message, chat)
                .orElse(buildMessage(chat.getId(), message.getText(), defaultKeyboard));
    }

    private Optional<SendMessage> buildMessage(Message message, Chat chat) {
        var keyboards = chat.getKeyboards();

        var inlineMessage = keyboards.stream()
                .flatMap(k -> k.getInlineRows().stream())
                .flatMap(r -> r.getButtons().stream())
                .filter(b -> b.getText().equals(message.getText()))
                .map(b -> {
                    return nonNull(b.getAttachedKeyboard())
                            ? buildMessage(chat.getId(), b.getDescription(), convert(b.getAttachedKeyboard()))
                            : buildMessage(chat.getId(), b.getDescription());

                })
                .findFirst();

        var replyMessage = keyboards.stream()
                .flatMap(k -> k.getReplyRows().stream())
                .flatMap(r -> r.getButtons().stream())
                .filter(b -> b.getText().equals(message.getText()))
                .map(b -> {
                     return nonNull(b.getAttachedKeyboard())
                            ? buildMessage(chat.getId(), b.getDescription(), convert(b.getAttachedKeyboard()))
                            : buildMessage(chat.getId(), b.getDescription());

                })
                .findFirst();

        return inlineMessage.isEmpty() ? replyMessage : inlineMessage;
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
}
