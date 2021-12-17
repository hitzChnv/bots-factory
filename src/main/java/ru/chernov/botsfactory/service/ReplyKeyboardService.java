package ru.chernov.botsfactory.service;

import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import ru.chernov.botsfactory.model.enums.ReplyKeyboardType;
import ru.chernov.botsfactory.model.keyboards.ReplyKeyboard;

import java.util.List;

public interface ReplyKeyboardService {

    ReplyKeyboardMarkup build(ReplyKeyboardType type);

    List<ReplyKeyboard> findAll();

    List<ReplyKeyboard> findAllByChatId(String chatId);
}
