package ru.chernov.botsfactory.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.chernov.botsfactory.model.enums.KeyboardType;
import ru.chernov.botsfactory.model.keyboards.Keyboard;
import ru.chernov.botsfactory.repository.KeyboardRepository;
import ru.chernov.botsfactory.service.KeyboardService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class KeyboardServiceImpl implements KeyboardService {

    private final KeyboardRepository repository;

    @Override
    @Transactional(readOnly = true)
    public List<Keyboard> findAll() {
        return repository.findAll();
    }

    @Override
    @Transactional
    public Keyboard findByType(KeyboardType type) {
        return repository.findByType(type);
    }
}
