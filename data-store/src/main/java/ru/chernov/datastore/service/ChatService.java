package ru.chernov.datastore.service;

import ru.chernov.datastore.model.entity.chat.Chat;

import java.util.Optional;

public interface ChatService {

    Chat create(String chatId);

    Optional<Chat> findById(String chatId);
}
