package ru.chernov.datastore.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.chernov.datastore.model.entity.chat.Chat;
import ru.chernov.datastore.model.entity.keyboard.Keyboard;
import ru.chernov.datastore.service.ChatService;
import ru.chernov.datastore.service.DataStoreService;
import ru.chernov.datastore.service.KeyboardService;

import java.util.Optional;

import static java.util.Optional.empty;

@Service
@RequiredArgsConstructor
public class DataStoreServiceImpl implements DataStoreService {

    private final KeyboardService keyboardService;
    private final ChatService chatService;

    @Override
    @Transactional
    public Optional<Keyboard> findInlineKeyboardByChatIdAndCommand(String chatId, String command) {
        var chat = findChatById(chatId);

        return chat.isEmpty()
                ? empty()
                : chat.get().getKeyboards().stream()
                .flatMap(k -> k.getInlineRows().stream())
                .flatMap(r -> r.getButtons().stream())
                .filter(b -> b.getText().equals(command))
                .map(b -> keyboardService.findById(b.getId()).get())
                .findFirst();
    }

    @Override
    @Transactional
    public Optional<Keyboard> findReplyKeyboardByChatIdAndCommand(String chatId, String command) {
        var chat = findChatById(chatId);

        return chat.isEmpty()
                ? empty()
                : chat.get().getKeyboards().stream()
                .flatMap(k -> k.getReplyRows().stream())
                .flatMap(r -> r.getButtons().stream())
                .filter(b -> b.getText().equals(command))
                .map(b -> keyboardService.findById(b.getId()).get())
                .findFirst();
    }

    @Override
    @Transactional
    public Optional<Chat> findChatById(String id) {
        return chatService.findById(id);
    }
}
