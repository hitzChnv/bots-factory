package ru.chernov.botsfactory.model.keyboards.buttons;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import ru.chernov.botsfactory.model.keyboards.Keyboard;

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

    @Column(name = "description")
    private String description;

    @OneToOne
    @JoinColumn(name = "attached_keyboard_id", referencedColumnName = "id")
    private Keyboard attachedKeyboard;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof InlineButton)) return false;
        InlineButton that = (InlineButton) o;

        return id.equals(that.id) && text.equals(that.text) && Objects.equals(url, that.url)
                && Objects.equals(callbackData, that.callbackData) && Objects.equals(pay, that.pay)
                && Objects.equals(description, that.description)
                && Objects.equals(attachedKeyboard, that.attachedKeyboard);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, text, url, callbackData, pay, description, attachedKeyboard);
    }
}
