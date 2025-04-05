package com.chatAny.chatapi.api;

import com.chatAny.chatapi.domain.room.Room;
import com.chatAny.chatapi.domain.user.User;
import com.chatAny.chatapi.service.RoomService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = {"http://localhost:4200", "http://localhost:5173", "http://localhost:3000"}, maxAge = 3600)
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
    public ResponseEntity<User> createRoom(@RequestParam("userId") String userId,
                                           @RequestParam("roomName") String roomName) {
        return roomService.createRoom(userId, roomName);
    }
}
