package ru.chernov.datastore.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.chernov.datastore.model.entity.button.Button;
import ru.chernov.datastore.model.entity.button.ReplyButton;

import java.util.Optional;

@Repository
public interface ReplyButtonRepository extends JpaRepository<ReplyButton, Long> {

    Optional<ReplyButton> findByText(String text);
}
