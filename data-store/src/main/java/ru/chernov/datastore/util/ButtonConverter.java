package ru.chernov.datastore.util;

import ru.chernov.datastore.model.dto.ButtonResponse;
import ru.chernov.datastore.model.entity.button.Button;

import java.util.List;

public final class ButtonConverter {

    private ButtonConverter() {
    }

    public static ButtonResponse convert(Button source) {
        return ButtonResponse.builder()
                .id(source.getId())
                .text(source.getText())
                .description(source.getDescription())
                .attachedKeyboardId(source.getAttachedKeyboardId())
                .build();
    }
}
