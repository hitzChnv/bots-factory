package ru.chernov.datastore.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.chernov.datastore.model.dto.KeyboardResponse;
import ru.chernov.datastore.service.KeyboardService;

import java.util.List;

import static org.springframework.http.ResponseEntity.notFound;
import static ru.chernov.datastore.model.enums.KeyboardType.DEFAULT_REPLY_KEYBOARD;
import static ru.chernov.datastore.util.KeyboardConverter.convert;
import static ru.chernov.datastore.util.KeyboardConverter.convertAll;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/data-store/api/keyboards/")
public class KeyboardController {

    private final KeyboardService keyboardService;

    @GetMapping
    public ResponseEntity<List<KeyboardResponse>> findAll() {
        return ResponseEntity.ok(convertAll(keyboardService.findAll()));
    }

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
