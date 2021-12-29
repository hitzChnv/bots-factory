package ru.chernov.app.model;

import lombok.Data;

@Data
public class Button {

    private Long id;

    private String text;

    private String description;

    private String attachedKeyboardId;
}
