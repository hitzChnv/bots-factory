package ru.chernov.botsfactory.model.keyboards.buttons;

import lombok.Data;

import javax.persistence.*;

import static javax.persistence.GenerationType.IDENTITY;

@Data

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
}
