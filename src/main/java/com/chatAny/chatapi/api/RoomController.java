package com.chatAny.chatapi.api;

import com.chatAny.chatapi.domain.room.Room;
import com.chatAny.chatapi.domain.user.User;
import com.chatAny.chatapi.dto.RoomCreateDto;
import com.chatAny.chatapi.service.RoomService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/rooms")
public class RoomController {

    RoomService roomService;

    public RoomController(RoomService roomService) {
        this.roomService = roomService;
    }

    @GetMapping()
    public ResponseEntity<List<Room>> getRooms() {
        return roomService.getRooms();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Room> getRoom(@PathVariable String id) {
        return roomService.getRoom(id);
    }

    @PostMapping()
    public ResponseEntity<User> createRoom(@Valid @RequestBody RoomCreateDto command) {
        return roomService.createRoom(command);
    }
}
