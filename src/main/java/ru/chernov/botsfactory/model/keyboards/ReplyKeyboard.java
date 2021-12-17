package ru.chernov.botsfactory.model.keyboards;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.hibernate.Hibernate;
import ru.chernov.botsfactory.model.entity.Chat;
import ru.chernov.botsfactory.model.enums.ReplyKeyboardType;
import ru.chernov.botsfactory.model.keyboards.buttons.ReplyKeyboardRow;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;

import static javax.persistence.GenerationType.IDENTITY;

@Getter
@Setter
@RequiredArgsConstructor

@Entity
@Table(schema = "bots_factory", name = "reply_keyboards")
public class ReplyKeyboard {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "reply_type")
    private ReplyKeyboardType type;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(schema = "bots_factory", name = "reply_keyboard_row_union",
            joinColumns = @JoinColumn(name = "reply_keyboard_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "reply_row_id", referencedColumnName = "id"))
    private List<ReplyKeyboardRow> rows;

    @ManyToMany
    @JoinTable(schema = "bots_factory", name = "chat_keyboard_union", joinColumns = {
            @JoinColumn(name = "reply_keyboard_id", referencedColumnName = "id")
    }, inverseJoinColumns = {
            @JoinColumn(name = "chat_id", referencedColumnName = "id")
    })
    private List<Chat> chats;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        ReplyKeyboard that = (ReplyKeyboard) o;
        return id != null && Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
