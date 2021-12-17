package ru.chernov.botsfactory.model.enums;

import static java.util.Arrays.stream;

public enum ReplyKeyboardType {

    DEFAULT_REPLY_KEYBOARD,

    QUESTIONS_REPLY_KEYBOARD,

    CONTACTS_REPLY_KEYBOARD;

    public static boolean hasType(String type) {
        return stream(values()).anyMatch(t -> t.name().equals(type));
    }
}
