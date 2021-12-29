package ru.chernov.datastore.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.chernov.datastore.model.entity.button.Button;
import ru.chernov.datastore.model.entity.button.InlineButton;
import ru.chernov.datastore.model.entity.button.ReplyButton;
import ru.chernov.datastore.repository.InlineButtonRepository;
import ru.chernov.datastore.repository.ReplyButtonRepository;
import ru.chernov.datastore.service.ButtonService;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ButtonServiceImpl implements ButtonService {

    private final ReplyButtonRepository replyButtonRepository;
    private final InlineButtonRepository inlineButtonRepository;

    @Override
    @Transactional
    public Optional<ReplyButton> findReplyByCommand(String command) {
        return replyButtonRepository.findByText(command);
    }

    @Override
    @Transactional
    public Optional<InlineButton> findInlineByCommand(String command) {
        return inlineButtonRepository.findByText(command);
    }
}
