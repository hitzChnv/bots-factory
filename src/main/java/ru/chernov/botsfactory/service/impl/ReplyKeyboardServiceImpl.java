package ru.chernov.botsfactory.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import ru.chernov.botsfactory.converter.ReplyKeyboardConverter;
import ru.chernov.botsfactory.model.enums.ReplyKeyboardType;
import ru.chernov.botsfactory.model.keyboards.ReplyKeyboard;
import ru.chernov.botsfactory.repository.ReplyKeyboardRepository;
import ru.chernov.botsfactory.service.ReplyKeyboardService;

import java.util.List;

import static java.util.Optional.ofNullable;

@Service
@RequiredArgsConstructor
public class ReplyKeyboardServiceImpl implements ReplyKeyboardService {

    private final ReplyKeyboardRepository repository;

    @Override
    @Transactional
    public ReplyKeyboardMarkup build(ReplyKeyboardType type) {
        var replyKeyboard = ofNullable(repository.findByType(type));
        if (replyKeyboard.isEmpty()) {
            return ReplyKeyboardMarkup.builder()
                    .build();
        }

        return ReplyKeyboardConverter.convert(replyKeyboard.get());
    }

    @Override
    @Transactional(readOnly = true)
    public List<ReplyKeyboard> findAll() {
        return repository.findAll();
    }

    @Override
    @Transactional
    public List<ReplyKeyboard> findAllByChatId(String chatId) {
        return repository.findAllByChatId(chatId);
    }
}
