package ru.chernov.app.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import ru.chernov.app.model.Button;
import ru.chernov.app.model.Chat;
import ru.chernov.app.model.Keyboard;

import java.util.List;
import java.util.Optional;

@FeignClient(value = "${data-store.config.value}", url = "${data-store.config.url}", decode404 = true)
public interface DataStoreClient {

    @GetMapping(value = "keyboards")
    List<Keyboard> findKeyboards();

    @GetMapping(value = "keyboards/{id}")
    Optional<Keyboard> findKeyboardById(@PathVariable Long id);

    @GetMapping(value = "keyboards/default")
    Keyboard findDefaultKeyboard();

    @GetMapping("chats/{id}")
    Optional<Chat> findChatById(@PathVariable String id);

    @PostMapping("chats/{id}")
    Chat create(@PathVariable String id);

    @GetMapping(value = "/keyboards/inline/{chatId}/{command}")
    Optional<Keyboard> findInlineKeyboardByIdsAndCommand(@PathVariable("chatId") String chatId,
                                                 @PathVariable String command);
    @GetMapping(value = "/keyboards/reply/{chatId}/{command}")
    Optional<Keyboard> findReplyKeyboardByIdsAndCommand(@PathVariable("chatId") String chatId,
                                                 @PathVariable String command);

    @GetMapping("buttons/{command}")
    Optional<Button> findButtonByCommand(@PathVariable String command);
}
