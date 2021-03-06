package ru.chernov.datastore.model.entity.keyboard;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import ru.chernov.datastore.model.entity.button.InlineKeyboardRow;
import ru.chernov.datastore.model.entity.button.ReplyKeyboardRow;
import ru.chernov.datastore.model.entity.chat.Chat;
import ru.chernov.datastore.model.enums.KeyboardType;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;

@Getter
@Setter
@RequiredArgsConstructor

@Entity
@Table(schema = "bots_factory", name = "keyboards")
public class Keyboard {

    @Id
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "type")
    private KeyboardType type;

    @ManyToMany
    @JoinTable(schema = "bots_factory", name = "keyboard_row_union",
            joinColumns = @JoinColumn(name = "keyboard_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "inline_row_id", referencedColumnName = "id"))
    private List<InlineKeyboardRow> inlineRows;

    @ManyToMany
    @JoinTable(schema = "bots_factory", name = "keyboard_row_union",
            joinColumns = @JoinColumn(name = "keyboard_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "reply_row_id", referencedColumnName = "id"))
    private List<ReplyKeyboardRow> replyRows;

    @ManyToMany
    @JoinTable(schema = "bots_factory", name = "chat_keyboard_union", joinColumns = {
            @JoinColumn(name = "keyboard_id", referencedColumnName = "id")
    }, inverseJoinColumns = {
            @JoinColumn(name = "chat_id", referencedColumnName = "id")
    })
    private List<Chat> chats;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Keyboard)) return false;
        Keyboard keyboard = (Keyboard) o;
        return Objects.equals(id, keyboard.id) && type == keyboard.type
                && Objects.equals(inlineRows, keyboard.inlineRows) && Objects.equals(replyRows, keyboard.replyRows)
                && Objects.equals(chats, keyboard.chats);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, type, inlineRows, replyRows, chats);
    }
}
