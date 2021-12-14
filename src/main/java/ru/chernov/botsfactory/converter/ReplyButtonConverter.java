package ru.chernov.botsfactory.converter;

import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import ru.chernov.botsfactory.model.keyboards.buttons.ReplyButton;

import java.util.List;
import java.util.stream.Collectors;

public final class ReplyButtonConverter {

    private ReplyButtonConverter() {
    }

    public static KeyboardButton convert(ReplyButton source) {
        return KeyboardButton.builder()
                .text(source.getText())
                .build();
    }

    public static List<KeyboardButton> convertAll(List<ReplyButton> sources) {
        return sources.stream()
                .map(ReplyButtonConverter::convert)
                .collect(Collectors.toList());
    }
}
