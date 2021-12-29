package ru.chernov.datastore.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.chernov.datastore.model.entity.button.InlineButton;

import java.util.Optional;

@Repository
public interface InlineButtonRepository extends JpaRepository<InlineButton, Long> {

    Optional<InlineButton> findByText(String text);
}
