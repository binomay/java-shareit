package ru.practicum.shareit.item.dto;

import lombok.Data;

@Data
public class InputCommentDto {
    private Integer id;
    private String text;
    private Integer itemId;
    private Integer authorId;
}