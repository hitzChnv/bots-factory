package ru.chernov.botsfactory.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import ru.chernov.botsfactory.converter.InlineKeyboardConverter;
import ru.chernov.botsfactory.model.enums.InlineKeyboardType;
import ru.chernov.botsfactory.model.keyboards.InlineKeyboard;
import ru.chernov.botsfactory.repository.InlineKeyboardRepository;
import ru.chernov.botsfactory.service.InlineKeyboardService;

import java.util.List;

import static java.util.Optional.ofNullable;

@Service
@RequiredArgsConstructor
public class InlineKeyboardServiceImpl implements InlineKeyboardService {

    private final InlineKeyboardRepository repository;

    @Override
    @Transactional
    public InlineKeyboardMarkup build(InlineKeyboardType type) {
        var replyKeyboard = ofNullable(repository.findByType(type));
        if (replyKeyboard.isEmpty()) {
            return InlineKeyboardMarkup.builder()
                    .build();
        }

        return InlineKeyboardConverter.convert(replyKeyboard.get());
    }

    @Override
    @Transactional(readOnly = true)
    public List<InlineKeyboard> findAll() {
        return repository.findAll();
    }

    @Override
    @Transactional
    public List<InlineKeyboard> findAllByChatId(String chatId) {
        return repository.findAllByChatId(chatId);
    }
}
