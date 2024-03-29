package ru.practicum.shareit.request.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class ItemRequestCreateDto {
    @NotBlank(message = "Не указано описание запроса!")
    private String description;
}

