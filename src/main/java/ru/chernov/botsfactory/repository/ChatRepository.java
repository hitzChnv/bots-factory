package ru.chernov.botsfactory.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.chernov.botsfactory.model.entity.Chat;

@Repository
public interface ChatRepository extends JpaRepository<Chat, String> {
}
