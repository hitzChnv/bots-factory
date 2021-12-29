package ru.chernov.datastore.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.chernov.datastore.model.dto.ButtonResponse;
import ru.chernov.datastore.service.ButtonService;

import static org.springframework.http.ResponseEntity.notFound;
import static ru.chernov.datastore.util.ButtonConverter.convert;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/data-store/api/buttons/")
public class ButtonController {

    private final ButtonService service;

    @GetMapping("/{command}")
    public ResponseEntity<ButtonResponse> findButton(@PathVariable String command) {
        var replyButton = service.findReplyByCommand(command);
        var inlineButton = service.findInlineByCommand(command);
        var button = replyButton.isEmpty() ? inlineButton : replyButton;

        return button.map(b -> ResponseEntity.ok(convert(b))).orElse(notFound().build());
    }
}
