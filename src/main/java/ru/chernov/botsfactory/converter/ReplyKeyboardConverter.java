package ru.chernov.botsfactory.converter;

import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import ru.chernov.botsfactory.model.keyboards.ReplyKeyboard;

import java.util.List;
import java.util.stream.Collectors;

public final class ReplyKeyboardConverter {

    private ReplyKeyboardConverter() {
    }

    public static ReplyKeyboardMarkup convert(ReplyKeyboard source) {
        return ReplyKeyboardMarkup.builder()
                .keyboard(ReplyKeyboardRowConverter.convertAll(source.getRows()))
                .resizeKeyboard(true)
                .build();
    }

    public static List<ReplyKeyboardMarkup> convertAll(List<ReplyKeyboard> sources) {
        return sources.stream()
                .map(ReplyKeyboardConverter::convert)
                .collect(Collectors.toList());
    }
}
