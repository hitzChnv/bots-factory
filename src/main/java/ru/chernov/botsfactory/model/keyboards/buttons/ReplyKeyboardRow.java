package ru.chernov.botsfactory.model.keyboards.buttons;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;

import static javax.persistence.GenerationType.IDENTITY;

@Getter
@Setter
@RequiredArgsConstructor

@Entity
@Table(schema = "bots_factory", name = "reply_keyboard_rows")
public class ReplyKeyboardRow {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(schema = "bots_factory", name = "reply_row_button_union", joinColumns = @JoinColumn(name = "reply_row_id"),
            inverseJoinColumns = @JoinColumn(name = "reply_button_id"))
    private List<ReplyButton> buttons;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ReplyKeyboardRow)) return false;
        ReplyKeyboardRow that = (ReplyKeyboardRow) o;

        return id.equals(that.id) && Objects.equals(buttons, that.buttons);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, buttons);
    }
}
