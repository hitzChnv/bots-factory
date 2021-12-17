package ru.chernov.botsfactory.model.keyboards.buttons;

import lombok.*;
import org.hibernate.Hibernate;

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
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        ReplyKeyboardRow that = (ReplyKeyboardRow) o;
        return id != null && Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
