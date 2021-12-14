package ru.chernov.botsfactory.model.keyboards.buttons;

import lombok.Data;

import javax.persistence.*;

import static javax.persistence.GenerationType.IDENTITY;

@Data

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
}
