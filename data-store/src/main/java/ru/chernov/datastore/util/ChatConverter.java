package ru.chernov.datastore.util;

import ru.chernov.datastore.model.dto.ChatResponse;
import ru.chernov.datastore.model.entity.chat.Chat;

import java.util.List;
import java.util.stream.Collectors;

public final class ChatConverter {

    private ChatConverter() {
    }

    public static ChatResponse convert(Chat chat) {
        return ChatResponse.builder()
                .id(chat.getId())
                .keyboards(KeyboardConverter.convertAll(chat.getKeyboards()))
                .build();
    }

    public static List<ChatResponse> convertAll(List<Chat> chats) {
        return chats.stream()
                .map(ChatConverter::convert)
                .collect(Collectors.toList());
    }
}
