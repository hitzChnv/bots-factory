package ru.chernov.botsfactory.service;

import ru.chernov.botsfactory.model.enums.KeyboardType;
import ru.chernov.botsfactory.model.keyboards.Keyboard;

import java.util.List;

public interface KeyboardService {

    List<Keyboard> findAll();

    Keyboard findByType(KeyboardType type);
}
