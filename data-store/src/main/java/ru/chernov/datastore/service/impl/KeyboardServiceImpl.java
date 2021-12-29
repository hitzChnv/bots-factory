package ru.chernov.datastore.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.chernov.datastore.model.entity.keyboard.Keyboard;
import ru.chernov.datastore.model.enums.KeyboardType;
import ru.chernov.datastore.repository.KeyboardRepository;
import ru.chernov.datastore.service.KeyboardService;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class KeyboardServiceImpl implements KeyboardService {

    private final KeyboardRepository repository;

    @Override
    @Transactional
    public List<Keyboard> findAll() {
        return repository.findAll();
    }

    @Override
    @Transactional
    public Optional<Keyboard> findByType(KeyboardType type) {
        return repository.findByType(type);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Keyboard> findById(Long id) {
        return repository.findById(id);
    }
}
