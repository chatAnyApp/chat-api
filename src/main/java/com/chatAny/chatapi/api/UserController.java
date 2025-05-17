package com.chatAny.chatapi.api;

import com.chatAny.chatapi.domain.user.User;
import com.chatAny.chatapi.dto.UserCreateDto;
import com.chatAny.chatapi.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;


@RestController
@RequestMapping("/users")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping()
    public User createUser(@Valid @RequestBody UserCreateDto command) {
        try {
            return this.userService.findOrCreateUser(command.getName());
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "An error has occurred, sorry.");
        }
    }

    @PutMapping("/{userId}/rooms/{roomId}")
    public ResponseEntity<User> enterRoom(@PathVariable String userId,
                                          @PathVariable String roomId) {
        return userService.enterRoom(userId, roomId);
    }

    @DeleteMapping("/{userId}/rooms/{roomId}")
    public ResponseEntity<User> leaveRoom(@PathVariable String userId,
                                          @PathVariable String roomId) {
        return userService.leaveRoom(userId, roomId);
    }
}
