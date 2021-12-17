package ru.chernov.botsfactory.service;

import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import ru.chernov.botsfactory.model.enums.InlineKeyboardType;
import ru.chernov.botsfactory.model.keyboards.InlineKeyboard;

import java.util.List;

public interface InlineKeyboardService {

    InlineKeyboardMarkup build(InlineKeyboardType type);

    List<InlineKeyboard> findAll();

    List<InlineKeyboard> findAllByChatId(String chatId);
}
