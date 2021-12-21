package ru.chernov.botsfactory.model.keyboards.buttons;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.chernov.botsfactory.model.keyboards.Keyboard;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

@MappedSuperclass
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public abstract class Button {

    @Id
    @Column(name = "id")
    private Long id;

    @Column(name = "text")
    private String text;

    @Column(name = "description")
    private String description;

    @OneToOne
    @JoinColumn(name = "attached_keyboard_id", referencedColumnName = "id")
    private Keyboard attachedKeyboard;
}
