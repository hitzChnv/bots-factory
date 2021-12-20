package ru.chernov.botsfactory.service;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;

public interface MessageService {

    SendMessage create(Message message);
}
