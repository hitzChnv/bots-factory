package ru.chernov.botsfactory.model.keyboards;

import lombok.Data;
import ru.chernov.botsfactory.model.enums.ReplyKeyboardType;
import ru.chernov.botsfactory.model.keyboards.buttons.ReplyKeyboardRow;

import javax.persistence.*;
import java.util.List;

import static javax.persistence.GenerationType.IDENTITY;

@Data

@Entity
@Table(schema = "bots_factory", name = "reply_keyboards")
public class ReplyKeyboard {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "reply_type")
    private ReplyKeyboardType type;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(schema = "bots_factory", name = "reply_keyboard_row_union",
            joinColumns = @JoinColumn(name = "reply_keyboard_id"),
            inverseJoinColumns = @JoinColumn(name = "reply_row_id"))
    private List<ReplyKeyboardRow> rows;
}
