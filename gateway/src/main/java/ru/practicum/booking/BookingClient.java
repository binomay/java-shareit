package ru.practicum.booking;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.util.DefaultUriBuilderFactory;
import ru.practicum.booking.dto.InputBookingDto;
import ru.practicum.client.BaseClient;

import java.util.Map;

@PropertySource("classpath:application.properties")
@Service
public class BookingClient extends BaseClient {
    private static final String API_PREFIX = "/bookings";

    @Autowired
    public BookingClient(@Value("${shareit-server.url}") String serverUrl, RestTemplateBuilder builder) {
        super(
                builder
                        .uriTemplateHandler(new DefaultUriBuilderFactory(serverUrl + API_PREFIX))
                        .requestFactory(HttpComponentsClientHttpRequestFactory::new)
                        .build()
        );
    }

    public ResponseEntity<Object> createBooking(long userId, InputBookingDto inputBookingDto) {
        return post("", userId, inputBookingDto);
    }

    public ResponseEntity<Object> updateBooking(long userId, long bookingId, Boolean isApprove) {
        return patch("/" + bookingId + "?approved=" + isApprove, userId);
    }

    public ResponseEntity<Object> getBookingById(long bookingId, long userId) {
        return get("/" + bookingId, userId);
    }


    public ResponseEntity<Object> getUsersBooking(long userId, String state, Integer from, Integer size) {
        Map<String, Object> parameters = Map.of(
                "from", from,
                "size", size,
                "state", state
        );
        return get("?from={from}&size={size}&state={state}", userId, parameters);
    }


    public ResponseEntity<Object> getBookingsForOwner(long userId, String state, Integer from, Integer size) {
        Map<String, Object> parameters = Map.of(
                "from", from,
                "size", size,
                "state", state
        );
        return get("/owner?from={from}&size={size}&state={state}", userId, parameters);
    }
}