package ru.chernov.botsfactory.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboard;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import ru.chernov.botsfactory.model.enums.InlineKeyboardType;
import ru.chernov.botsfactory.model.keyboards.buttons.InlineKeyboardRow;
import ru.chernov.botsfactory.repository.InlineKeyboardRepository;
import ru.chernov.botsfactory.service.InlineKeyboardService;

import java.util.List;
import java.util.stream.Collectors;

import static ru.chernov.botsfactory.converter.InlineButtonConverter.convertAll;

@Service
@RequiredArgsConstructor
public class InlineKeyboardServiceImpl implements InlineKeyboardService {

    private final InlineKeyboardRepository repository;

    @Override
    @Transactional
    public ReplyKeyboard build(InlineKeyboardType type) {
        var replyKeyboard = repository.findByType(type);

        List<InlineKeyboardRow> rows = replyKeyboard.getRows();
        List<List<InlineKeyboardButton>> keyboard = rows.stream()
                .map(r -> convertAll(r.getButtons()))
                .collect(Collectors.toList());


        return InlineKeyboardMarkup.builder()
                .keyboard(keyboard)
                .build();
    }
}
