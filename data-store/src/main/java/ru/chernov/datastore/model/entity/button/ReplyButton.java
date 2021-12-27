package ru.chernov.datastore.model.entity.button;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Objects;

@Getter
@Setter
@ToString
@RequiredArgsConstructor

@Entity
@Table(schema = "bots_factory", name = "reply_buttons")
public class ReplyButton extends Button {

    @Column(name = "request_contact")
    private Boolean requestContact;

    @Column(name = "request_location")
    private Boolean requestLocation;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ReplyButton)) return false;
        ReplyButton that = (ReplyButton) o;
        return Objects.equals(requestContact, that.requestContact)
                && Objects.equals(requestLocation, that.requestLocation);
    }

    @Override
    public int hashCode() {
        return Objects.hash(requestContact, requestLocation);
    }
}
