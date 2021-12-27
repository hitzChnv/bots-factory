package ru.chernov.datastore.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.chernov.datastore.model.dto.ChatRequest;
import ru.chernov.datastore.model.dto.ChatResponse;
import ru.chernov.datastore.service.ChatService;

import static org.springframework.http.ResponseEntity.notFound;
import static org.springframework.http.ResponseEntity.ok;
import static ru.chernov.datastore.util.ChatConverter.convert;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/data-store/api/chats/")
public class ChatController {

    private final ChatService chatService;

    @GetMapping("{id}")
    public ResponseEntity<ChatResponse> findById(@PathVariable String id) {
        return chatService.findById(id)
                .map(c -> ok(convert(c)))
                .orElse(notFound().build());
    }

    @PostMapping
    public ResponseEntity<ChatResponse> create(@RequestBody ChatRequest chatRequest) {
        var chat = chatService.create(chatRequest.getId());

        return ok(convert(chat));
    }
}
