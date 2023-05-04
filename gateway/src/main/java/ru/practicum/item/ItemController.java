package ru.practicum.item;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.item.dto.InputCommentDto;
import ru.practicum.item.dto.ItemDto;

import javax.validation.Valid;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;

@Validated
@Slf4j
@RestController
@RequestMapping("/items")
public class ItemController {

    private final ItemClient  itemClient;

    public ItemController(ItemClient itemClient) {
        this.itemClient = itemClient;
    }

    @GetMapping("/{itemId}")
    public ResponseEntity<Object> getItemById(@PathVariable Integer itemId,
                                              @RequestHeader(value = "X-Sharer-User-Id") Integer userId) {
        log.info("Item controller! вощли сюда!");
        return itemClient.getItemDtoById(itemId, userId);
    }

    @GetMapping
    public ResponseEntity<Object> getUsersItems(@RequestHeader(value = "X-Sharer-User-Id") Integer ownerId,
                                                  @RequestParam(name = "from", defaultValue = "0") @PositiveOrZero Integer from,
                                                  @RequestParam(name = "size", defaultValue = "10") @Positive Integer size) {
        return itemClient.getUsersItems(ownerId, from, size);

    }

    @GetMapping("/search")
    public ResponseEntity<Object> getItemsByContextSearch(@RequestHeader(value = "X-Sharer-User-Id") Integer userId,
                                                 @RequestParam("text") String context,
                                                 @RequestParam(name = "from", defaultValue = "0") @PositiveOrZero Integer from,
                                                 @RequestParam(name = "size", defaultValue = "10") @Positive Integer size) {
        return itemClient.getItemsByContextSearch(userId, context, from, size);
    }

    @PostMapping
    public ResponseEntity<Object> createItem(@RequestBody @Valid ItemDto itemDto, @RequestHeader(value = "X-Sharer-User-Id") Integer userId) {
        return itemClient.createItem(userId,itemDto);
    }

    @PatchMapping("/{itemId}")
    public ResponseEntity<Object> updateItem(@RequestBody ItemDto itemDto,
                              @PathVariable Integer itemId,
                              @RequestHeader(value = "X-Sharer-User-Id") Integer ownerId) {
        itemDto.setOwner(ownerId);
        itemDto.setId(itemId);
        return itemClient.updateItem(ownerId, itemId, itemDto);
    }

    @PostMapping("/{itemId}/comment")
    public ResponseEntity<Object> addComment(@RequestBody @Valid InputCommentDto commentDto,
                                       @PathVariable Integer itemId,
                                       @RequestHeader(value = "X-Sharer-User-Id") Integer authorId) {
        return itemClient.addCommentToItem(authorId, itemId, commentDto);
    }

}