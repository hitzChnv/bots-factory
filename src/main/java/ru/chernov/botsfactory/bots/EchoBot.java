package ru.chernov.botsfactory.bots;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboard;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import ru.chernov.botsfactory.model.entity.Chat;
import ru.chernov.botsfactory.model.enums.InlineKeyboardType;
import ru.chernov.botsfactory.model.enums.ReplyKeyboardType;
import ru.chernov.botsfactory.model.keyboards.buttons.InlineButton;
import ru.chernov.botsfactory.model.keyboards.buttons.ReplyButton;
import ru.chernov.botsfactory.service.ChatService;
import ru.chernov.botsfactory.service.InlineKeyboardService;
import ru.chernov.botsfactory.service.ReplyKeyboardService;

import java.util.Optional;

import static java.util.Objects.nonNull;

/**
 * Simple echo bot
 */
@Component
@RequiredArgsConstructor
public class EchoBot extends TelegramLongPollingBot {

    @Value("${echobot.token}")
    private String token;

    @Value("${echobot.username}")
    private String username;

    private final ReplyKeyboardService replyService;
    private final InlineKeyboardService inlineService;
    private final ChatService chatService;

    @Override
    public String getBotToken() {
        return token;
    }

    @Override
    public String getBotUsername() {
        return username;
    }

    @Override
    @SneakyThrows(TelegramApiException.class)
    public void onUpdateReceived(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()) {
            var message = update.getMessage();

            execute(create(message));

        } else if (update.hasCallbackQuery()) {
            var message = update.getCallbackQuery().getMessage();

            execute(create(message));
        }
    }

    private SendMessage create(Message message) {
        var chatId = message.getChatId().toString();

        var optionalChat = chatService.findById(chatId);
        var chat = optionalChat.isEmpty() ? chatService.create(chatId) : optionalChat.get();

        var inlineButton = findInlineButton(message, chat);
        var replyButton = findReplyButton(message, chat);

        if (replyButton.isEmpty() && inlineButton.isPresent()) {
            return create(chatId, inlineButton.get());

        } else if (replyButton.isPresent() && inlineButton.isEmpty()) {
            return create(chatId, replyButton.get());

        } else {
            return buildDefaultMessage(message);

        }
    }

    private Optional<InlineButton> findInlineButton(Message message, Chat chat) {
        var inlineKeyboards = inlineService.findAllByChatId(chat.getId());

        return inlineKeyboards.stream()
                .flatMap(k -> k.getRows().stream())
                .flatMap(r -> r.getButtons().stream())
                .filter(b -> b.getText().equals(message.getText()))
                .findFirst();
    }

    private Optional<ReplyButton> findReplyButton(Message message, Chat chat) {
        var replyKeyboards = replyService.findAllByChatId(chat.getId());

        return replyKeyboards.stream()
                .flatMap(k -> k.getRows().stream())
                .flatMap(r -> r.getButtons().stream())
                .filter(b -> b.getText().equals(message.getText()))
                .findFirst();
    }

    private SendMessage create(String chatId, InlineButton button) {
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

    private SendMessage create(String chatId, ReplyButton button) {
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
