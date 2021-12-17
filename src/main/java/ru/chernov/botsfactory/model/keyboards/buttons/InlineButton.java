package ru.chernov.botsfactory.model.keyboards.buttons;

import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.*;

import java.util.Objects;

import static javax.persistence.GenerationType.IDENTITY;

@Getter
@Setter
@ToString
@RequiredArgsConstructor

@Entity
@Table(schema = "bots_factory", name = "inline_buttons")
public class InlineButton {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "text")
    private String text;

    @Column(name = "url")
    private String url;

    @Column(name = "callback_data")
    private String callbackData;

    @Column(name = "pay")
    private Boolean pay;

    @Column(name = "next_message_text")
    private String nextMessageText;

    @Column(name = "next_keyboard_type")
    private String nextKeyboardType;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        InlineButton that = (InlineButton) o;
        return id != null && Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
