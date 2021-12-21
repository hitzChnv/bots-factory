package ru.chernov.botsfactory.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.chernov.botsfactory.model.enums.KeyboardType;
import ru.chernov.botsfactory.model.keyboards.Keyboard;

@Repository
public interface KeyboardRepository extends JpaRepository<Keyboard, Long> {

    Keyboard findByType(KeyboardType type);
}
