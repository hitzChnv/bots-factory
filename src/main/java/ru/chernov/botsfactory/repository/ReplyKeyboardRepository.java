package ru.chernov.botsfactory.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.chernov.botsfactory.model.enums.ReplyKeyboardType;
import ru.chernov.botsfactory.model.keyboards.ReplyKeyboard;

import java.util.List;

@Repository
public interface ReplyKeyboardRepository extends JpaRepository<ReplyKeyboard, Long> {

    ReplyKeyboard findByType(ReplyKeyboardType replyKeyboardType);

    @Query("SELECT ik FROM ReplyKeyboard ik INNER JOIN FETCH ik.chats")
    List<ReplyKeyboard> findAllByChatId(String chatId);
}
