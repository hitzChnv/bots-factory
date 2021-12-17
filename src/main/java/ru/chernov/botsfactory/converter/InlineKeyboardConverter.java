package ru.chernov.botsfactory.converter;

import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import ru.chernov.botsfactory.model.keyboards.InlineKeyboard;

import java.util.List;
import java.util.stream.Collectors;

public final class InlineKeyboardConverter {

    private InlineKeyboardConverter() {
    }

    public static InlineKeyboardMarkup convert(InlineKeyboard source) {
        return InlineKeyboardMarkup.builder()
                .keyboard(source.getRows().stream()
                        .map(r -> InlineButtonConverter.convertAll(r.getButtons()))
                        .collect(Collectors.toList()))
                .build();
    }

    public static List<InlineKeyboardMarkup> convertAll(List<InlineKeyboard> sources) {
        return sources.stream()
                .map(InlineKeyboardConverter::convert)
                .collect(Collectors.toList());
    }
}
