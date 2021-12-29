package ru.chernov.app.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import ru.chernov.app.model.Chat;
import ru.chernov.app.model.Keyboard;

import java.util.Optional;

@FeignClient(value = "${data-store.config.value}", url = "${data-store.config.url}", decode404 = true)
public interface DataStoreClient {

    @GetMapping(value = "keyboards/{id}")
    Optional<Keyboard> findKeyboardById(@PathVariable Long id);

    @GetMapping(value = "keyboards/default")
    Keyboard findDefaultKeyboard();

    @GetMapping("chats/{id}")
    Optional<Chat> findChatById(@PathVariable String id);

    @PostMapping("chats/{id}")
    Chat create(@PathVariable String id);
}
