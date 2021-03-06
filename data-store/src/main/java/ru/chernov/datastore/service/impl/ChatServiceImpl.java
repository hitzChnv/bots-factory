package ru.chernov.datastore.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.chernov.datastore.model.entity.chat.Chat;
import ru.chernov.datastore.repository.ChatRepository;
import ru.chernov.datastore.service.ChatService;
import ru.chernov.datastore.service.KeyboardService;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ChatServiceImpl implements ChatService {

    private final ChatRepository repository;
    private final KeyboardService keyboardService;

    @Override
    @Transactional
    public Chat create(String chatId) {
        var chat = new Chat(chatId, keyboardService.findAll());

        return repository.save(chat);
    }

    @Override
    @Transactional
    public Optional<Chat> findById(String chatId) {
        return repository.findById(chatId);
    }
}
