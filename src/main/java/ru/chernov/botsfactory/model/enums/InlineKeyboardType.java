package ru.chernov.botsfactory.model.enums;

import static java.util.Arrays.stream;

public enum InlineKeyboardType {

    MUSIC_INLINE_KEYBOARD,

    VIDEO_INLINE_KEYBOARD;

    public static boolean hasType(String type) {
        return stream(values()).anyMatch(t -> t.name().equals(type));
    }
}
