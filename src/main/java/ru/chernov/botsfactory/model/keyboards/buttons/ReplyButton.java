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
@Table(schema = "bots_factory", name = "reply_buttons")
public class ReplyButton {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "text")
    private String text;

    @Column(name = "request_contact")
    private Boolean requestContact;

    @Column(name = "request_location")
    private Boolean requestLocation;

    @Column(name = "description")
    private String description;

    @OneToOne
    @JoinColumn(name = "attached_keyboard_id", referencedColumnName = "id")
    private Keyboard attachedKeyboard;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ReplyButton)) return false;
        ReplyButton that = (ReplyButton) o;

        return id.equals(that.id) && text.equals(that.text) && Objects.equals(requestContact, that.requestContact)
                && Objects.equals(requestLocation, that.requestLocation)
                && Objects.equals(description, that.description)
                && Objects.equals(attachedKeyboard, that.attachedKeyboard);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, text, requestContact, requestLocation, description, attachedKeyboard);
    }
}
