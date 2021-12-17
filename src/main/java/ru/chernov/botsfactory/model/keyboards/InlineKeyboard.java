package ru.chernov.botsfactory.model.keyboards;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import ru.chernov.botsfactory.model.entity.Chat;
import ru.chernov.botsfactory.model.enums.InlineKeyboardType;
import ru.chernov.botsfactory.model.keyboards.buttons.InlineKeyboardRow;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;

import static javax.persistence.GenerationType.IDENTITY;

@Getter
@Setter
@RequiredArgsConstructor

@Entity
@Table(schema = "bots_factory", name = "inline_keyboards")
public class InlineKeyboard {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "inline_type")
    private InlineKeyboardType type;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(schema = "bots_factory", name = "inline_keyboard_row_union",
            joinColumns = @JoinColumn(name = "inline_keyboard_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "inline_row_id", referencedColumnName = "id"))
    private List<InlineKeyboardRow> rows;

    @ManyToMany
    @JoinTable(schema = "bots_factory", name = "chat_keyboard_union", joinColumns = {
            @JoinColumn(name = "inline_keyboard_id", referencedColumnName = "id")
    }, inverseJoinColumns = {
            @JoinColumn(name = "chat_id", referencedColumnName = "id")
    })
    private List<Chat> chats;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof InlineKeyboard)) return false;
        InlineKeyboard that = (InlineKeyboard) o;

        return id.equals(that.id) && type == that.type && Objects.equals(rows, that.rows) && Objects.equals(chats, that.chats);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, type, rows, chats);
    }
}
