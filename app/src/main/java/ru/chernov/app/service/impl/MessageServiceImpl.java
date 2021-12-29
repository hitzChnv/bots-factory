package ru.chernov.app.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboard;
import ru.chernov.app.model.Chat;
import ru.chernov.app.model.Keyboard;
import ru.chernov.app.service.DataStoreClient;
import ru.chernov.app.service.MessageService;

import java.util.Optional;
import java.util.function.Function;

import static java.util.Objects.nonNull;
import static ru.chernov.app.util.KeyboardConverter.convert;

@Service
@RequiredArgsConstructor
public class MessageServiceImpl implements MessageService {

    private final DataStoreClient dataStoreClient;

    @Override
    public SendMessage create(Message message) {
        var chatId = message.getChatId().toString();

        var savedChat = dataStoreClient.findChatById(chatId);
        var chat = savedChat.isEmpty() ? dataStoreClient.create(chatId) : savedChat.get();

        return create(message, chat);
    }

    private SendMessage create(Message message, Chat chat) {
        var defaultKeyboard = findDefaultKeyboard();

        return buildMessage(message, chat)
                .orElse(buildMessage(chat.getId(), message.getText(), defaultKeyboard));
    }

    private Optional<SendMessage> buildMessage(Message message, Chat chat) {
        var keyboards = chat.getKeyboards();

        Function<Keyboard.InlineRow.InlineButton, SendMessage> inlineKeyboardFunction = b -> nonNull(b.getKeyboardId())
                ? buildMessage(chat.getId(), b.getDescription(), findKeyboardById(b.getKeyboardId()))
                : buildMessage(chat.getId(), b.getDescription());

        Function<Keyboard.ReplyRow.ReplyButton, SendMessage> replyKeyboardFunction = b -> nonNull(b.getKeyboardId())
                ? buildMessage(chat.getId(), b.getDescription(), findKeyboardById(b.getKeyboardId()))
                : buildMessage(chat.getId(), b.getDescription());

        var messageDefinedInlineKeyboard = keyboards.stream()
                .flatMap(k -> k.getInlineRows().stream())
                .flatMap(r -> r.getInlineButtons().stream())
                .filter(b -> b.getText().equals(message.getText()))
                .map(inlineKeyboardFunction)
                .findFirst();

        var messageDefinedReplyKeyboard = keyboards.stream()
                .flatMap(k -> k.getReplyRows().stream())
                .flatMap(r -> r.getReplyButtons().stream())
                .filter(b -> b.getText().equals(message.getText()))
                .map(replyKeyboardFunction)
                .findFirst();

        return messageDefinedInlineKeyboard.isEmpty() ? messageDefinedReplyKeyboard : messageDefinedInlineKeyboard;
    }

    private ReplyKeyboard findKeyboardById(Long id) {
        Optional<Keyboard> keyboard = dataStoreClient.findKeyboardById(id);

        return keyboard.isEmpty() ? findDefaultKeyboard() : convert(keyboard.get());
    }

    private ReplyKeyboard findDefaultKeyboard() {
        return convert(dataStoreClient.findDefaultKeyboard());
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
