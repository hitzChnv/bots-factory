package ru.chernov.app.util;

import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import ru.chernov.app.model.Keyboard;

import java.util.List;
import java.util.stream.Collectors;

public final class KeyboardConverter {

    //private static final List<KeyboardType> REPLY_TYPES = of(DEFAULT_REPLY_KEYBOARD, CONTACTS_REPLY_KEYBOARD, QUESTIONS_REPLY_KEYBOARD);
    //private static final List<KeyboardType> INLINE_TYPES = of(MUSIC_INLINE_KEYBOARD, VIDEO_INLINE_KEYBOARD);

    private KeyboardConverter() {
    }

    /*public static ReplyKeyboard convert(Keyboard source) {
        var replyKeyboard = convertReply(source)

        var inlineKeyboard = INLINE_TYPES.stream()
                .filter(t -> nonNull(source.getType()) && t.toString().equals(source.getType()))
                .map(t -> convertInline(source))
                .findFirst();

        return replyKeyboard.isEmpty()
                ? inlineKeyboard.orElseThrow(() -> new IllegalArgumentException(format("Wrong type format: %s", source.getType())))
                : replyKeyboard.get();
    }*/

    public static InlineKeyboardMarkup convertInline(Keyboard source) {
        return InlineKeyboardMarkup.builder()
                .keyboard(source.getInlineRows().stream()
                        .map(r -> convertInlineButtons(r.getInlineButtons()))
                        .collect(Collectors.toList()))
                .build();
    }

    public static ReplyKeyboardMarkup convertReply(Keyboard source) {
        return ReplyKeyboardMarkup.builder()
                .keyboard(source.getReplyRows().stream()
                        .map(r -> new KeyboardRow(convertReplyButtons(r.getReplyButtons())))
                        .collect(Collectors.toList()))
                .resizeKeyboard(true)
                .build();
    }

    private static List<InlineKeyboardButton> convertInlineButtons(List<Keyboard.InlineRow.InlineButton> buttons) {
        return buttons.stream()
                .map(b -> InlineKeyboardButton.builder()
                        .text(b.getText())
                        .callbackData(b.getCallbackData())
                        .pay(b.getPay())
                        .url(b.getUrl())
                        .build())
                .collect(Collectors.toList());
    }

    private static List<KeyboardButton> convertReplyButtons(List<Keyboard.ReplyRow.ReplyButton> buttons) {
        return buttons.stream()
                .map(b -> KeyboardButton.builder()
                        .text(b.getText())
                        .requestContact(b.getRequestContact())
                        .requestLocation(b.getRequestLocation())
                        .build())
                .collect(Collectors.toList());
    }
}
