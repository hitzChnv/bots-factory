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
@Table(schema = "bots_factory", name = "inline_buttons")
public class InlineButton extends Button {

    @Column(name = "url")
    private String url;

    @Column(name = "callback_data")
    private String callbackData;

    @Column(name = "pay")
    private Boolean pay;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof InlineButton)) return false;
        InlineButton that = (InlineButton) o;
        return Objects.equals(url, that.url) && Objects.equals(callbackData, that.callbackData)
                && Objects.equals(pay, that.pay);
    }

    @Override
    public int hashCode() {
        return Objects.hash(url, callbackData, pay);
    }
}
