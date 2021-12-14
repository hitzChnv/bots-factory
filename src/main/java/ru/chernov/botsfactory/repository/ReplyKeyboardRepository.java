package ru.chernov.botsfactory.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.chernov.botsfactory.model.enums.ReplyKeyboardType;
import ru.chernov.botsfactory.model.keyboards.ReplyKeyboard;

@Repository
public interface ReplyKeyboardRepository extends JpaRepository<ReplyKeyboard, Long> {

    ReplyKeyboard findByType(ReplyKeyboardType replyKeyboardType);
}
