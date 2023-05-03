package ru.practicum.request;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.request.dto.ItemRequestCreateDto;

import javax.validation.Valid;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;

@Validated
@RestController
@RequestMapping(path = "/requests")
public class ItemRequestController {

    private final RequestClient client;

    public ItemRequestController(RequestClient client) {
        this.client = client;
    }

    @PostMapping
    public ResponseEntity<Object> createRequest(@RequestHeader("X-Sharer-User-Id") long userId,
                                        @Valid @RequestBody ItemRequestCreateDto itemRequestDto) {
        return client.createRequest(userId, itemRequestDto);
    }

    //получить список своих запросов вместе с данными об ответах на них.
    @GetMapping
    public ResponseEntity<Object> getRequestsByUser(@RequestHeader("X-Sharer-User-Id") long userId) {
        return client.getRequestsByUser(userId);
    }

    @GetMapping("/{requestId}")
    public ResponseEntity<Object> getById(@RequestHeader("X-Sharer-User-Id") long userId,
                                         @PathVariable Integer requestId) {
        return client.getItemRequestDtoById(requestId, userId);
    }

    @GetMapping("/all")
    public ResponseEntity<Object> getAll(@RequestHeader("X-Sharer-User-Id") long userId,
                                               @RequestParam(name = "from", defaultValue = "0") @PositiveOrZero Integer from,
                                               @RequestParam(name = "size", defaultValue = "10") @Positive Integer size) {
        return client.getAll(userId, from, size);
    }
}
