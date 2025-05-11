package com.chatAny.chatapi.service;

import com.chatAny.chatapi.domain.room.Room;
import com.chatAny.chatapi.domain.room.RoomRepository;
import com.chatAny.chatapi.domain.user.User;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;

@Service
public class RoomService {

    private final RoomRepository roomRepository;
    private final UserService userService;

    public RoomService(RoomRepository roomRepository, UserService userService) {
        this.roomRepository = roomRepository;
        this.userService = userService;
    }

    public ResponseEntity<List<Room>> getRooms() {
        String frontendUrl = System.getenv("FRONTEND_URL");
        System.out.println("FRONTEND_URL: " + frontendUrl);
        Sort sort = Sort.by(Sort.Order.asc("date"));
        List<Room> rooms = roomRepository.findAllBy(sort);
        return ResponseEntity.ok(rooms);
    }

    public ResponseEntity<User> createRoom(String userId, String roomName) {
        // todo room name max length check
        Room room = new Room()
                .setName(roomName)
                .setCreatedDate(Instant.now())
                .setNumberOfUsers(0);

        room = roomRepository.save(room);

        return userService.enterRoom(userId, room.getId());
    }

    public ResponseEntity<Room> getRoom(String id) {
        Room room = roomRepository.findById(id).orElse(null);
        return ResponseEntity.ok(room);
    }
}
