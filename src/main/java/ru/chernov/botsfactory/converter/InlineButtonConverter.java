package ru.chernov.botsfactory.converter;

import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import ru.chernov.botsfactory.model.keyboards.buttons.InlineButton;

import java.util.List;
import java.util.stream.Collectors;

public final class InlineButtonConverter {

    private InlineButtonConverter() {
    }

    public static InlineKeyboardButton convert(InlineButton source) {
        return InlineKeyboardButton.builder()
                .text(source.getText())
                .url(source.getUrl())
                .callbackData(source.getCallbackData())
                .pay(source.getPay())
                .build();
    }

    public static List<InlineKeyboardButton> convertAll(List<InlineButton> sources) {
        return sources.stream()
                .map(InlineButtonConverter::convert)
                .collect(Collectors.toList());
    }
}
