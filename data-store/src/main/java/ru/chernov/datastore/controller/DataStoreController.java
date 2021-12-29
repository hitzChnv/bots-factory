package ru.chernov.datastore.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.chernov.datastore.model.dto.KeyboardResponse;
import ru.chernov.datastore.service.DataStoreService;

import static org.springframework.http.ResponseEntity.notFound;
import static ru.chernov.datastore.util.KeyboardConverter.convert;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/data-store/api/")
public class DataStoreController {

    private final DataStoreService dataStoreService;

    @GetMapping("/keyboards/inline/{chatId}/{command}")
    public ResponseEntity<KeyboardResponse> findInlineByChatIdAndCommand(@PathVariable("chatId") String chatId,
                                                                         @PathVariable String command) {
        var keyboard = dataStoreService.findInlineKeyboardByChatIdAndCommand(chatId, command);

        return keyboard.map(k -> ResponseEntity.ok(convert(k))).orElse(notFound().build());
    }

    @GetMapping("/keyboards/reply/{chatId}/{command}")
    public ResponseEntity<KeyboardResponse> findReplyByChatIdAndCommand(@PathVariable("chatId") String chatId,
                                                                        @PathVariable String command) {
        var keyboard = dataStoreService.findReplyKeyboardByChatIdAndCommand(chatId, command);

        return keyboard.map(k -> ResponseEntity.ok(convert(k))).orElse(notFound().build());
    }
}
