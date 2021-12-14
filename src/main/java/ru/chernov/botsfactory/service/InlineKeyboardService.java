package ru.chernov.botsfactory.service;

import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboard;
import ru.chernov.botsfactory.model.enums.InlineKeyboardType;

public interface InlineKeyboardService {

    ReplyKeyboard build(InlineKeyboardType type);
}
