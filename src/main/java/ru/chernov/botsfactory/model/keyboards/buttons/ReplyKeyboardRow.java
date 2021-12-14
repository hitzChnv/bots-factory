package ru.chernov.botsfactory.model.keyboards.buttons;

import lombok.Data;

import javax.persistence.*;
import java.util.List;

import static javax.persistence.GenerationType.IDENTITY;

@Data

@Entity
@Table(schema = "bots_factory", name = "reply_keyboard_rows")
public class ReplyKeyboardRow {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(schema = "bots_factory", name = "reply_row_button_union", joinColumns = @JoinColumn(name = "reply_row_id"),
    inverseJoinColumns = @JoinColumn(name = "reply_button_id"))
    private List<ReplyButton> buttons;
}
