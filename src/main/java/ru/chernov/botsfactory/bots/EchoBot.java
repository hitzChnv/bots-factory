package ru.chernov.botsfactory.bots;

import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.ApplicationScope;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

/**
 * Simple echo bot
 */
@Component
@ApplicationScope
public class EchoBot extends TelegramLongPollingBot {

    @Value("${echobot.token}")
    private String token;

    @Value("${echobot.username}")
    private String username;

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
            var message = update.getMessage();

            execute(SendMessage.builder()
                    .chatId(message.getChatId().toString())
                    .text(message.getText())
                    .build());
        }
    }
}
