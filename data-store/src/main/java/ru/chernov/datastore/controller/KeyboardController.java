package ru.chernov.datastore.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.chernov.datastore.model.dto.KeyboardResponse;
import ru.chernov.datastore.service.KeyboardService;

import java.util.List;

import static org.springframework.http.ResponseEntity.notFound;
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
        return keyboardService.findById(id)
                .map(k -> ResponseEntity.ok(convert(k)))
                .orElse(notFound().build());
    }
}
