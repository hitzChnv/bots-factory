package ru.chernov.botsfactory.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboard;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import ru.chernov.botsfactory.converter.ReplyKeyboardRowConverter;
import ru.chernov.botsfactory.model.enums.ReplyKeyboardType;
import ru.chernov.botsfactory.model.keyboards.buttons.ReplyKeyboardRow;
import ru.chernov.botsfactory.repository.ReplyKeyboardRepository;
import ru.chernov.botsfactory.service.ReplyKeyboardService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ReplyKeyboardServiceImpl implements ReplyKeyboardService {

    private final ReplyKeyboardRepository repository;

    @Override
    @Transactional
    public ReplyKeyboard build(ReplyKeyboardType type) {
        var replyKeyboard = repository.findByType(type);

        List<ReplyKeyboardRow> rows = replyKeyboard.getRows();
        List<KeyboardRow> keyboardRows = ReplyKeyboardRowConverter.convertAll(rows);

        return ReplyKeyboardMarkup.builder()
                .keyboard(keyboardRows)
                .resizeKeyboard(true)
                .build();
    }
}
