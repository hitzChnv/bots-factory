package ru.chernov.app.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboard;
import ru.chernov.app.model.Chat;
import ru.chernov.app.model.Keyboard.InlineRow.InlineButton;
import ru.chernov.app.model.Keyboard.ReplyRow.ReplyButton;
import ru.chernov.app.service.DataStoreClient;
import ru.chernov.app.service.MessageService;

import java.util.Optional;
import java.util.function.Function;
import java.util.function.Predicate;

import static java.util.Objects.nonNull;
import static ru.chernov.app.util.KeyboardConverter.convertInline;
import static ru.chernov.app.util.KeyboardConverter.convertReply;

@Service
@RequiredArgsConstructor
public class MessageServiceImpl implements MessageService {

    private final DataStoreClient dataStoreClient;

    @Override
    public SendMessage create(Message message) {
        var chatId = message.getChatId().toString();
        var chat = findChatById(chatId);

        return buildMessage(message, chat)
                .orElse(buildMessage(chatId, message.getText(), findDefaultKeyboard()));
    }

    private Optional<SendMessage> buildMessage(Message message, Chat chat) {
        var keyboards = chat.getKeyboards();

        Predicate<String> buttonPredicate = s -> s.equals(message.getText());

        Function<InlineButton, SendMessage> inlineKeyboardFunction = b -> nonNull(b.getKeyboardId())
                ? buildMessage(chat.getId(), b.getDescription(), findKeyboardById(b.getKeyboardId()))
                : buildMessage(chat.getId(), b.getDescription());

        Function<ReplyButton, SendMessage> replyKeyboardFunction = b -> nonNull(b.getKeyboardId())
                ? buildMessage(chat.getId(), b.getDescription(), findKeyboardById(b.getKeyboardId()))
                : buildMessage(chat.getId(), b.getDescription());

        var inlineSendMessage = keyboards.stream()
                .flatMap(k -> k.getInlineRows().stream())
                .flatMap(r -> r.getInlineButtons().stream())
                .filter(b -> buttonPredicate.test(b.getText()))
                .map(inlineKeyboardFunction)
                .findFirst();

        var replySendMessage = keyboards.stream()
                .flatMap(k -> k.getReplyRows().stream())
                .flatMap(r -> r.getReplyButtons().stream())
                .filter(b -> buttonPredicate.test(b.getText()))
                .map(replyKeyboardFunction)
                .findFirst();

        return inlineSendMessage.isEmpty() ? replySendMessage : inlineSendMessage;
    }

    private Chat findChatById(String id) {
        var chat = dataStoreClient.findChatById(id);

        return chat.isEmpty() ? dataStoreClient.create(id) : chat.get();
    }

    private ReplyKeyboard findKeyboardById(Long id) {
        var keyboard = dataStoreClient.findKeyboardById(id);
        if (keyboard.isPresent()) {
            return keyboard.get().getReplyRows().isEmpty()
                    ? convertInline(keyboard.get())
                    : convertReply(keyboard.get());
        }

        return findDefaultKeyboard();
    }

    private ReplyKeyboard findDefaultKeyboard() {
        return convertReply(dataStoreClient.findDefaultKeyboard());
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
