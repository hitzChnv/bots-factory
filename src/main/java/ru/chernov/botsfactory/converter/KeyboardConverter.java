package ru.chernov.botsfactory.converter;

import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboard;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import ru.chernov.botsfactory.model.enums.KeyboardType;
import ru.chernov.botsfactory.model.keyboards.Keyboard;

import java.util.List;
import java.util.stream.Collectors;

import static java.lang.String.format;
import static java.util.List.of;
import static java.util.Objects.nonNull;
import static ru.chernov.botsfactory.model.enums.KeyboardType.*;

public final class KeyboardConverter {

    private static final List<KeyboardType> REPLY_TYPES = of(DEFAULT_REPLY_KEYBOARD, CONTACTS_REPLY_KEYBOARD, QUESTIONS_REPLY_KEYBOARD);
    private static final List<KeyboardType> INLINE_TYPES = of(MUSIC_INLINE_KEYBOARD, VIDEO_INLINE_KEYBOARD);

    private KeyboardConverter() {
    }

    public static ReplyKeyboard convert(Keyboard source) {
        var replyKeyboard = REPLY_TYPES.stream()
                .filter(t -> nonNull(source.getType()) && t.equals(source.getType()))
                .map(t -> convertReply(source))
                .findFirst();

        var inlineKeyboard = INLINE_TYPES.stream()
                .filter(t -> nonNull(source.getType()) && t.equals(source.getType()))
                .map(t -> convertInline(source))
                .findFirst();

        return replyKeyboard.isEmpty()
                ? inlineKeyboard.orElseThrow(() -> new IllegalArgumentException(format("Wrong type format: %s", source.getType())))
                : replyKeyboard.get();
    }

    private static InlineKeyboardMarkup convertInline(Keyboard source) {
        return InlineKeyboardMarkup.builder()
                .keyboard(source.getInlineRows().stream()
                        .map(r -> InlineButtonConverter.convertAll(r.getButtons()))
                        .collect(Collectors.toList()))
                .build();
    }

    private static ReplyKeyboardMarkup convertReply(Keyboard source) {
        return ReplyKeyboardMarkup.builder()
                .keyboard(ReplyKeyboardRowConverter.convertAll(source.getReplyRows()))
                .resizeKeyboard(true)
                .build();
    }
}
