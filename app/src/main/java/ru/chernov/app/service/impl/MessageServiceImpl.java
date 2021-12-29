package ru.chernov.app.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboard;
import ru.chernov.app.model.Chat;
import ru.chernov.app.service.DataStoreClient;
import ru.chernov.app.service.MessageService;

import static ru.chernov.app.util.KeyboardConverter.convertInline;
import static ru.chernov.app.util.KeyboardConverter.convertReply;

@Service
@RequiredArgsConstructor
public class MessageServiceImpl implements MessageService {

    private final DataStoreClient dataStoreClient;

    @Override
    public SendMessage create(Message message) {
        var chatId = message.getChatId().toString();

        var savedChat = dataStoreClient.findChatById(chatId);
        var chat = savedChat.isEmpty() ? dataStoreClient.create(chatId) : savedChat.get();

        return buildMessage(message, chat);
    }

    /*private SendMessage create(Message message, Chat chat) {


        return buildMessage(message, chat).orElse(buildMessage(chat.getId(), message.getText(), defaultKeyboard));
    }*/

    private SendMessage buildMessage(Message message, Chat chat) {
        var defaultKeyboard = findDefaultKeyboard();
        var button = dataStoreClient.findButtonByCommand(message.getText());

        var inlineKeyboard = dataStoreClient.findInlineKeyboardByIdsAndCommand(chat.getId(), message.getText());
        var replyKeyboard = dataStoreClient.findReplyKeyboardByIdsAndCommand(chat.getId(), message.getText());

        SendMessage sendMessage;
        if (inlineKeyboard.isPresent()) {
            sendMessage = buildMessage(chat.getId(),
                    button.isPresent()
                            ? button.get().getDescription()
                            : inlineKeyboard.get().getTitle(),
                    convertInline(inlineKeyboard.get()));
        } else if (replyKeyboard.isPresent()) {
            sendMessage = buildMessage(chat.getId(),
                    button.isPresent()
                            ? button.get().getDescription()
                            : replyKeyboard.get().getTitle(),
                    convertReply(replyKeyboard.get()));
        } else {
            sendMessage = buildMessage(chat.getId(), message.getText(), defaultKeyboard);
        }

        return sendMessage;

        /*Optional<SendMessage> sendMessageReply;
        if (replyKeyboard.isEmpty()) {
            if (button.isPresent()) {
                sendMessageReply = buildMessage(chat.getId(), button.get().getDescription());
            }
        } else {
            if (button.isPresent()) {
                sendMessageReply = buildMessage(chat.getId(),
                        button.get().getDescription(),
                        convert(replyKeyboard.get()));
            }
        }*/



        /*Function<Keyboard.InlineRow.InlineButton, SendMessage> inlineKeyboardFunction = b -> nonNull(b.getKeyboardId())
                ? buildMessage(chat.getId(), b.getDescription(), findKeyboardById(b.getKeyboardId()))
                : buildMessage(chat.getId(), b.getDescription());

        Function<Keyboard.ReplyRow.ReplyButton, SendMessage> replyKeyboardFunction = b -> nonNull(b.getKeyboardId())
                ? buildMessage(chat.getId(), b.getDescription(), findKeyboardById(b.getKeyboardId()))
                : buildMessage(chat.getId(), b.getDescription());*/


        //return messageDefinedInlineKeyboard.isEmpty() ? messageDefinedReplyKeyboard : messageDefinedInlineKeyboard;
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
