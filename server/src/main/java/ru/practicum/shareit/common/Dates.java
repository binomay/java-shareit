package ru.practicum.shareit.common;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;

public  class Dates {
    public static LocalDateTime getCurrentDateTime() {
        ZoneId zoneId = ZoneId.of("Europe/Moscow");
        ZonedDateTime znd = ZonedDateTime.now(zoneId);
        return znd.toLocalDateTime();
    }
}
