package ru.chernov.botsfactory.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.chernov.botsfactory.model.entity.Chat;
import ru.chernov.botsfactory.repository.ChatRepository;
import ru.chernov.botsfactory.service.ChatService;
import ru.chernov.botsfactory.service.InlineKeyboardService;
import ru.chernov.botsfactory.service.ReplyKeyboardService;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ChatServiceImpl implements ChatService {

    private final ChatRepository repository;
    private final InlineKeyboardService inlineKeyboardService;
    private final ReplyKeyboardService replyKeyboardService;

    @Override
    @Transactional
    public Chat create(String chatId) {
        var chat = new Chat(chatId,
                replyKeyboardService.findAll(),
                inlineKeyboardService.findAll());

        return repository.save(chat);
    }

    @Override
    @Transactional
    public Optional<Chat> findById(String chatId) {
        return repository.findById(chatId);
    }
}
