package ru.chernov.botsfactory.converter;

import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import ru.chernov.botsfactory.model.keyboards.buttons.ReplyKeyboardRow;

import java.util.List;
import java.util.stream.Collectors;

public final class ReplyKeyboardRowConverter {

    private ReplyKeyboardRowConverter() {
    }

    public static KeyboardRow convert(ReplyKeyboardRow source) {
        return new KeyboardRow(ReplyButtonConverter.convertAll(source.getButtons()));
    }

    public static List<KeyboardRow> convertAll(List<ReplyKeyboardRow> sources) {
        return sources.stream()
                .map(ReplyKeyboardRowConverter::convert)
                .collect(Collectors.toList());
    }
}
