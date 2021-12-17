package ru.chernov.botsfactory.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.chernov.botsfactory.model.enums.InlineKeyboardType;
import ru.chernov.botsfactory.model.keyboards.InlineKeyboard;

import java.util.List;

@Repository
public interface InlineKeyboardRepository extends JpaRepository<InlineKeyboard, Long> {

    InlineKeyboard findByType(InlineKeyboardType inlineKeyboardType);

    @Query("SELECT ik FROM InlineKeyboard ik INNER JOIN FETCH ik.chats")
    List<InlineKeyboard> findAllByChatId(String chatId);
}
