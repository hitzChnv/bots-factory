package ru.chernov.datastore.model.entity.chat;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import ru.chernov.datastore.model.entity.keyboard.Keyboard;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;

@Getter
@Setter
@AllArgsConstructor
@RequiredArgsConstructor

@Entity
@Table(schema = "bots_factory", name = "chats")
public class Chat {

    @Id
    private String id;

    @ManyToMany
    @JoinTable(schema = "bots_factory", name = "chat_keyboard_union",
            joinColumns = @JoinColumn(name = "chat_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "keyboard_id", referencedColumnName = "id"))
    private List<Keyboard> keyboards;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Chat)) return false;
        Chat chat = (Chat) o;
        return Objects.equals(id, chat.id) && Objects.equals(keyboards, chat.keyboards);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, keyboards);
    }
}
