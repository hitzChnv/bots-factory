package ru.chernov.botsfactory.model.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import ru.chernov.botsfactory.model.keyboards.InlineKeyboard;
import ru.chernov.botsfactory.model.keyboards.ReplyKeyboard;

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

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(schema = "bots_factory", name = "chat_keyboard_union",
            joinColumns = @JoinColumn(name = "chat_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "reply_keyboard_id", referencedColumnName = "id"))
    private List<ReplyKeyboard> replyKeyboards;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(schema = "bots_factory", name = "chat_keyboard_union",
            joinColumns = @JoinColumn(name = "chat_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "inline_keyboard_id", referencedColumnName = "id"))
    private List<InlineKeyboard> inlineKeyboards;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Chat)) return false;
        Chat chat = (Chat) o;
        return id.equals(chat.id) && Objects.equals(replyKeyboards, chat.replyKeyboards)
                && Objects.equals(inlineKeyboards, chat.inlineKeyboards);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, replyKeyboards, inlineKeyboards);
    }
}

