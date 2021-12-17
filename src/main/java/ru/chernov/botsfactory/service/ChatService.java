package ru.chernov.botsfactory.service;

import ru.chernov.botsfactory.model.entity.Chat;

import java.util.Optional;

public interface ChatService {

    Chat create(String chatId);

    Optional<Chat> findById(String chatId);
}
