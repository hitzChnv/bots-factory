package ru.chernov.datastore.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.chernov.datastore.model.dto.KeyboardResponse;
import ru.chernov.datastore.service.KeyboardService;

import static org.springframework.http.ResponseEntity.notFound;
import static ru.chernov.datastore.model.enums.KeyboardType.DEFAULT_REPLY_KEYBOARD;
import static ru.chernov.datastore.util.KeyboardConverter.convert;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/data-store/api/keyboards/")
public class KeyboardController {

    private final KeyboardService keyboardService;

    @GetMapping("{id}")
    public ResponseEntity<KeyboardResponse> findById(@PathVariable Long id) {
        var keyboard = keyboardService.findById(id);

        return keyboard.map(k -> ResponseEntity.ok(convert(k))).orElse(notFound().build());
    }

    @GetMapping("/default")
    public ResponseEntity<KeyboardResponse> findDefault() {
        return keyboardService.findByType(DEFAULT_REPLY_KEYBOARD)
                .map(k -> ResponseEntity.ok(convert(k)))
                .orElse(notFound().build());
    }
}
