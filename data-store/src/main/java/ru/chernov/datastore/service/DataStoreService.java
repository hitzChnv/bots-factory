package ru.chernov.datastore.service;

import ru.chernov.datastore.model.entity.chat.Chat;
import ru.chernov.datastore.model.entity.keyboard.Keyboard;

import java.util.Optional;

public interface DataStoreService {

    Optional<Keyboard> findInlineKeyboardByChatIdAndCommand(String chatId, String command);

    Optional<Keyboard> findReplyKeyboardByChatIdAndCommand(String chatId, String command);

    Optional<Chat> findChatById(String id);
}
