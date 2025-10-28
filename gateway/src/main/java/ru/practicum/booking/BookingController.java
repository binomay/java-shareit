package ru.practicum.booking;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.booking.dto.InputBookingDto;

import javax.validation.Valid;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;

@Slf4j
@Validated
@RestController
@RequestMapping(path = "/bookings")
public class BookingController {
    private final BookingClient client;

    public BookingController(BookingClient client) {
        this.client = client;
    }

    @PostMapping
    public ResponseEntity<Object> createBooking(@Valid @RequestBody InputBookingDto inputBookingDto, @RequestHeader(value = "X-Sharer-User-Id") Integer bookerId) {
        log.info("Create booking for itemId = {}", inputBookingDto.getItemId());
        return client.createBooking(bookerId, inputBookingDto);
    }

    //Эндпоинт — PATCH /bookings/{bookingId}?approved={approved}, параметр approved может принимать значения true или false.
    @PatchMapping("/{bookingId}")
    public ResponseEntity<Object> approveBooking(@RequestHeader(value = "X-Sharer-User-Id") Integer userId, @PathVariable("bookingId") Integer bookingId, @RequestParam(name = "approved") Boolean isApprove) {
        log.info("Approve booking for itemId = {} approve = {}", userId, isApprove);
        return client.updateBooking(userId, bookingId, isApprove);
    }

    @GetMapping("/{bookingId}")
    public ResponseEntity<Object> getBooking(@RequestHeader(value = "X-Sharer-User-Id") Integer userId, @PathVariable("bookingId") Integer bookingId) {
        log.info("Get booking for bookingId ={}", bookingId);
        return client.getBookingById(bookingId, userId);
    }

    @GetMapping
    public ResponseEntity<Object> getBookingsForUser(@RequestHeader(value = "X-Sharer-User-Id") Integer userId,
                                                     @RequestParam(name = "from", defaultValue = "0")  @PositiveOrZero Integer from,
                                                     @RequestParam(name = "size", defaultValue = "10") @Positive Integer size,
                                                     @RequestParam(name = "state", defaultValue = "ALL") String state) {
        log.info("Get booking for user id ={} and state = {}", userId, state);
        return client.getUsersBooking(userId, state, from, size);
    }

    @GetMapping("/owner")
    public ResponseEntity<Object> getBookingsForOwner(@RequestHeader(value = "X-Sharer-User-Id") Integer userId,
                                                      @RequestParam(name = "from", defaultValue = "0") @PositiveOrZero Integer from,
                                                      @RequestParam(name = "size", defaultValue = "10") @Positive Integer size,
                                                      @RequestParam(name = "state", defaultValue = "ALL") String state) {
        log.info("Get user by id ={}", userId);
        return client.getBookingsForOwner(userId, state, from, size);
    }
}
