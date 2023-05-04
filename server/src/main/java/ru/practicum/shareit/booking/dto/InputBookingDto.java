package ru.practicum.shareit.booking.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class InputBookingDto {
    private Integer id;
    LocalDateTime start;
    LocalDateTime end;
    Integer itemId;
    Integer bookerId;
    String status;
}
