package ru.chernov.datastore.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.chernov.datastore.model.entity.keyboard.Keyboard;
import ru.chernov.datastore.model.enums.KeyboardType;

import java.util.Optional;

@Repository
public interface KeyboardRepository extends JpaRepository<Keyboard, Long> {

    Optional<Keyboard> findByType(KeyboardType type);
}
