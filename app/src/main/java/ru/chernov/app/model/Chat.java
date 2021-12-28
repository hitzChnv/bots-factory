package ru.chernov.app.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class Chat {

    private String id;

    @JsonProperty("keyboards")
    private List<Keyboard> keyboards;
}
