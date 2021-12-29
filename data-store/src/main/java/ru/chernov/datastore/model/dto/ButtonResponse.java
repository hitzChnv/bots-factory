package ru.chernov.datastore.model.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class ButtonResponse {

    private Long id;

    private String text;

    private String description;

    private Long attachedKeyboardId;
}
