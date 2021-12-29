package ru.chernov.datastore.service;

import ru.chernov.datastore.model.entity.button.InlineButton;
import ru.chernov.datastore.model.entity.button.ReplyButton;

import java.util.Optional;

public interface ButtonService {

    Optional<ReplyButton> findReplyByCommand(String command);

    Optional<InlineButton> findInlineByCommand(String command);
}
