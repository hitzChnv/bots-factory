package ru.chernov.datastore.service;

import ru.chernov.datastore.model.entity.keyboard.Keyboard;
import ru.chernov.datastore.model.enums.KeyboardType;

import java.util.List;
import java.util.Optional;

public interface KeyboardService {

    List<Keyboard> findAll();

    Keyboard findByType(KeyboardType type);

    Optional<Keyboard> findById(Long id);
}
