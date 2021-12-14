package ru.chernov.botsfactory.model.keyboards.buttons;

import lombok.Data;

import javax.persistence.*;
import java.util.List;

import static javax.persistence.GenerationType.IDENTITY;

@Data

@Entity
@Table(schema = "bots_factory", name = "inline_keyboard_rows")
public class InlineKeyboardRow {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(schema = "bots_factory", name = "inline_row_button_union", joinColumns = @JoinColumn(name = "inline_row_id"),
            inverseJoinColumns = @JoinColumn(name = "inline_button_id"))
    private List<InlineButton> buttons;
}
