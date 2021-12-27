package ru.chernov.datastore.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.chernov.datastore.model.entity.chat.Chat;

@Repository
public interface ChatRepository extends JpaRepository<Chat, String> {
}

