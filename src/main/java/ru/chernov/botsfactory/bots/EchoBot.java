package ru.chernov.botsfactory.bots;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboard;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import ru.chernov.botsfactory.service.InlineKeyboardService;
import ru.chernov.botsfactory.service.ReplyKeyboardService;

import java.util.function.Predicate;

import static ru.chernov.botsfactory.model.enums.InlineKeyboardType.MUSIC_INLINE_KEYBOARD;
import static ru.chernov.botsfactory.model.enums.InlineKeyboardType.VIDEO_INLINE_KEYBOARD;
import static ru.chernov.botsfactory.model.enums.ReplyKeyboardType.*;

/**
 * Simple echo bot
 */
@Component
@RequiredArgsConstructor
public class EchoBot extends TelegramLongPollingBot {

    @Value("${echobot.token}")
    private String token;

    @Value("${echobot.username}")
    private String username;

    private final ReplyKeyboardService replyService;
    private final InlineKeyboardService inlineService;

    @Override
    public String getBotToken() {
        return token;
    }

    @Override
    public String getBotUsername() {
        return username;
    }

    @Override
    @SneakyThrows(TelegramApiException.class)
    public void onUpdateReceived(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()) {
            var message = buildMessage(update);

            execute(message);
        }
    }

    private SendMessage buildMessage(Update update) {
        var message = update.getMessage();
        var chatId = message.getChatId().toString();

        Predicate<Message> isContacts = m -> m.getText().startsWith("Контакты");
        Predicate<Message> isShow = m -> m.getText().startsWith("Показать");
        Predicate<Message> isSearch = m -> m.getText().startsWith("Найти");
        Predicate<Message> isShowQuestions = m -> m.getText().startsWith("Показать вопросы");
        Predicate<Message> isQuestions = m -> m.getText().startsWith("Часто задаваемые вопросы");
        Predicate<Message> isMusic = m -> m.getText().startsWith("Музыка");
        Predicate<Message> isVideo = m -> m.getText().startsWith("Видео");
        Predicate<Message> isMenu = m -> m.getText().startsWith("Меню");

        if (isContacts.test(message)) {
            return buildReplyKeyboardMessage(chatId, "You are in contacts main menu. Choose action.",
                    replyService.build(CONTACTS_REPLY_KEYBOARD));

        } else if (isShow.test(message)) {
            return buildMessage(chatId, "You can try contact with " + message.getFrom().getUserName() + "account");

        } else if (isSearch.test(message)) {
            return buildMessage(chatId, "Try this: www.google.com");

        } else if (isQuestions.test(message)) {
            return buildReplyKeyboardMessage(chatId, "FAQ:\nAnswers: www.answrers.com", replyService.build(QUESTIONS_REPLY_KEYBOARD));

        } else if (isShowQuestions.test(message)) {
            return buildMessage(chatId, "FAQ:\nQuestions and answers: www.questions.com");

        } else if (isMusic.test(message)) {
            return buildInlineKeyboardMessage(chatId, "Follow the link if you want to listen to music!",
                    inlineService.build(MUSIC_INLINE_KEYBOARD));

        } else if (isVideo.test(message)) {
            return buildInlineKeyboardMessage(chatId, "Follow the link if you want to watch the video!",
                    inlineService.build(VIDEO_INLINE_KEYBOARD));

        } else if (isMenu.test(message)) {
            return buildReplyKeyboardMessage(chatId, "Main menu.", replyService.build(DEFAULT_REPLY_KEYBOARD));

        } else {
            return buildReplyKeyboardMessage(chatId, message.getText(), replyService.build(DEFAULT_REPLY_KEYBOARD));
        }
    }

    private SendMessage buildReplyKeyboardMessage(String chatId, String text, ReplyKeyboard keyboard) {
        return SendMessage.builder()
                .chatId(chatId)
                .text(text)
                .replyMarkup(keyboard)
                .build();
    }

    private SendMessage buildInlineKeyboardMessage(String chatId, String text, ReplyKeyboard keyboard) {
        return SendMessage.builder()
                .chatId(chatId)
                .text(text)
                .replyMarkup(keyboard)
                .build();
    }

    private SendMessage buildMessage(String chatId, String text) {
        return SendMessage.builder()
                .chatId(chatId)
                .text(text)
                .build();
    }
}
