package ru.chernov.botsfactory.model.keyboards;

import lombok.Data;
import ru.chernov.botsfactory.model.enums.InlineKeyboardType;
import ru.chernov.botsfactory.model.keyboards.buttons.InlineKeyboardRow;

import javax.persistence.*;
import java.util.List;

import static javax.persistence.GenerationType.IDENTITY;

@Data

@Entity
@Table(schema = "bots_factory", name = "inline_keyboards")
public class InlineKeyboard {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "inline_type")
    private InlineKeyboardType type;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(schema = "bots_factory", name = "inline_keyboard_row_union",
            joinColumns = @JoinColumn(name = "inline_keyboard_id"),
            inverseJoinColumns = @JoinColumn(name = "inline_row_id"))
    private List<InlineKeyboardRow> rows;
}
