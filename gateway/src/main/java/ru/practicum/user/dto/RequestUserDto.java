package ru.practicum.user.dto;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;

@Data
public class RequestUserDto {
    private Integer id;
    @NotBlank
    private String name;
    @NotEmpty(message = "Не указан e-mail")
    @Email(message = "Некорректный e-mail")
    private String email;
}
