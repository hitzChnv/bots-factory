package ru.chernov.datastore.model.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Builder
public class ChatResponse {

    private String id;

    private List<KeyboardResponse> keyboards;
}
