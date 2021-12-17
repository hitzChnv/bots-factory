package ru.chernov.botsfactory.bots;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import ru.chernov.botsfactory.model.entity.Chat;
import ru.chernov.botsfactory.model.enums.InlineKeyboardType;
import ru.chernov.botsfactory.model.enums.ReplyKeyboardType;
import ru.chernov.botsfactory.model.keyboards.InlineKeyboard;
import ru.chernov.botsfactory.model.keyboards.buttons.InlineButton;
import ru.chernov.botsfactory.model.keyboards.buttons.ReplyButton;
import ru.chernov.botsfactory.service.ChatService;
import ru.chernov.botsfactory.service.InlineKeyboardService;
import ru.chernov.botsfactory.service.ReplyKeyboardService;

import java.util.List;
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

            execute(buildMessage(message));

        } else if (update.hasCallbackQuery()) {
            var message = update.getCallbackQuery().getMessage();

            execute(buildMessage(message));
        }
    }

    private SendMessage buildMessage(Message message) {
        var chatId = message.getChatId().toString();

        Optional<Chat> optionalChat = chatService.findById(chatId);
        Chat chat = optionalChat.isEmpty() ? chatService.create(chatId) : optionalChat.get();

        var inlineButton = findInlineButton(message, chat);
        var replyButton = findReplyButton(message, chat);

        if (replyButton.isEmpty() && inlineButton.isPresent()) {
            return buildMessage(chatId, inlineButton.get());

        } else if (replyButton.isPresent() && inlineButton.isEmpty()) {
            return buildMessage(chatId, replyButton.get());

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

    private SendMessage buildMessage(String chatId, InlineButton button) {
        String type = button.getNextKeyboardType();

        if (nonNull(type)) {
            var keyboard = InlineKeyboardType.hasType(type)
                    ? inlineService.build(InlineKeyboardType.valueOf(type))
                    : replyService.build(ReplyKeyboardType.valueOf(type));

            return SendMessage.builder()
                    .chatId(chatId)
                    .text(button.getNextMessageText())
                    .replyMarkup(keyboard)
                    .build();
        }

        return SendMessage.builder()
                .chatId(chatId)
                .text(button.getNextMessageText())
                .build();

    }

    private SendMessage buildMessage(String chatId, ReplyButton button) {
        String type = button.getNextKeyboardType();

        if (nonNull(type)) {
            var keyboard = ReplyKeyboardType.hasType(type)
                    ? replyService.build(ReplyKeyboardType.valueOf(type))
                    : inlineService.build(InlineKeyboardType.valueOf(type));

            return SendMessage.builder()
                    .chatId(chatId)
                    .text(button.getNextMessageText())
                    .replyMarkup(keyboard)
                    .build();
        }

        return SendMessage.builder()
                .chatId(chatId)
                .text(button.getNextMessageText())
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
