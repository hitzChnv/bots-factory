package ru.chernov.botsfactory.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.chernov.botsfactory.model.enums.InlineKeyboardType;
import ru.chernov.botsfactory.model.keyboards.InlineKeyboard;

@Repository
public interface InlineKeyboardRepository extends JpaRepository<InlineKeyboard, Long> {

    InlineKeyboard findByType(InlineKeyboardType inlineKeyboardType);
}
