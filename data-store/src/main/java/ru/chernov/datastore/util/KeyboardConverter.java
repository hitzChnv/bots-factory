package ru.chernov.datastore.util;

import ru.chernov.datastore.model.dto.KeyboardResponse;
import ru.chernov.datastore.model.entity.keyboard.Keyboard;

import java.util.List;
import java.util.stream.Collectors;

public final class KeyboardConverter {

    private KeyboardConverter() {
    }

    public static KeyboardResponse convert(Keyboard keyboard){
        return KeyboardResponse.builder()
                .id(keyboard.getId())
                .type(keyboard.getType().toString())
                .inlineRowsResponse(keyboard.getInlineRows().stream()
                        .map(r -> KeyboardResponse.InlineRowResponse.builder()
                                .id(r.getId())
                                .inlineButtonsResponse(r.getButtons().stream()
                                        .map(b -> KeyboardResponse.InlineRowResponse.InlineButtonResponse.builder()
                                                .id(b.getId())
                                                .text(b.getText())
                                                .description(b.getDescription())
                                                .url(b.getUrl())
                                                .callbackData(b.getCallbackData())
                                                .pay(b.getPay())
                                                .keyboardId(b.getAttachedKeyboardId())
                                                .build())
                                        .collect(Collectors.toList()))
                                .build())
                        .collect(Collectors.toList()))
                .replyRowsResponse(keyboard.getReplyRows().stream()
                        .map(r -> KeyboardResponse.ReplyRowResponse.builder()
                                .id(r.getId())
                                .replyButtonsResponse(r.getButtons().stream()
                                        .map(b -> KeyboardResponse.ReplyRowResponse.ReplyButtonResponse.builder()
                                                .id(b.getId())
                                                .text(b.getText())
                                                .description(b.getDescription())
                                                .requestContact(b.getRequestContact())
                                                .requestLocation(b.getRequestLocation())
                                                .keyboardId(b.getAttachedKeyboardId())
                                                .build())
                                        .collect(Collectors.toList()))
                                .build())
                        .collect(Collectors.toList()))
                .build();
    }

    public static List<KeyboardResponse> convertAll(List<Keyboard> keyboards) {
        return keyboards.stream()
                .map(KeyboardConverter::convert)
                .collect(Collectors.toList());
    }
}
