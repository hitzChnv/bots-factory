package ru.chernov.botsfactory.service;

import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboard;
import ru.chernov.botsfactory.model.enums.ReplyKeyboardType;

public interface ReplyKeyboardService {

    ReplyKeyboard build(ReplyKeyboardType type);
}
