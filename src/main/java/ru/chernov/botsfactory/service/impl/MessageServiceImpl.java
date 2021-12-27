package ru.chernov.botsfactory.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboard;
import ru.chernov.botsfactory.model.dto.KeyboardRequest;
import ru.chernov.botsfactory.service.MessageService;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MessageServiceImpl implements MessageService {

    private final RestTemplate restTemplate;

    @Override
    public SendMessage create(Message message) {
        /*var chatId = message.getChatId().toString();

        var savedChat = chatService.findById(chatId);
        var chat = savedChat.isEmpty() ? chatService.create(chatId) : savedChat.get();

        return create(message, chat);*/
        return null;
    }

    /*private SendMessage create(Message message, Chat chat) {
        var defaultKeyboard = convertWithType(keyboardService.findByType(DEFAULT_REPLY_KEYBOARD));

        return buildMessage(message, chat)
                .orElse(buildMessage(chat.getId(), message.getText(), defaultKeyboard));
    }

    private Optional<SendMessage> buildMessage(Message message, Chat chat) {
        var keyboards = chat.getKeyboards();

        Function<Button, SendMessage> sendMessageFunction = b -> nonNull(b.getAttachedKeyboard())
                ? buildMessage(chat.getId(), b.getDescription(), convertWithType(b.getAttachedKeyboard()))
                : buildMessage(chat.getId(), b.getDescription());

        var messageDefinedInlineKeyboard = keyboards.stream()
                .flatMap(k -> k.getInlineRows().stream())
                .flatMap(r -> r.getButtons().stream())
                .filter(b -> b.getText().equals(message.getText()))
                .map(sendMessageFunction)
                .findFirst();

        var messageDefinedReplyKeyboard = keyboards.stream()
                .flatMap(k -> k.getReplyRows().stream())
                .flatMap(r -> r.getButtons().stream())
                .filter(b -> b.getText().equals(message.getText()))
                .map(sendMessageFunction)
                .findFirst();

        return messageDefinedInlineKeyboard.isEmpty() ? messageDefinedReplyKeyboard : messageDefinedInlineKeyboard;
    }*/



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

    private List<KeyboardRequest> findKeyboards() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_XML);
        HttpEntity<KeyboardRequest[]> request = new HttpEntity<>(headers);

        var response = restTemplate
                .exchange("http://localhost:7070/data-store/api/keyboards/", HttpMethod.GET,
                        request, new ParameterizedTypeReference<List<KeyboardRequest>>(){});

        return response.getBody();
    }
}
