package ru.practicum.user;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.user.dto.RequestUserDto;

import javax.validation.Valid;

@Slf4j
@Validated
@RestController
@RequestMapping(path = "/users")
public class UserController {

    private final UserClient userClient;

    public UserController(UserClient userClient) {
        this.userClient = userClient;
    }

    @GetMapping("/{userId}")
    ResponseEntity<Object> getUserById(@PathVariable Integer userId) {
        log.info("Get user by id ={}", userId);
        return userClient.getUserById(userId);
    }

    @GetMapping
    public ResponseEntity<Object> getUserList() {
        log.info("Get All users ");
        return userClient.getAllUsers();
    }

    @PostMapping
    public ResponseEntity<Object> createUser(@RequestBody @Valid RequestUserDto userDto) {
        return userClient.createUser(userDto);
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<Object> deleteUser(@PathVariable Integer userId) {
        return userClient.deleteUser(userId);
    }

    @PatchMapping("/{userId}")
    public ResponseEntity<Object> updateUser(@RequestBody RequestUserDto requestUserDto, @PathVariable Integer userId) {
        return userClient.updateUser(userId, requestUserDto);
    }
}


