package com.chatAny.chatapi.service;

import com.chatAny.chatapi.domain.room.Room;
import com.chatAny.chatapi.domain.room.RoomRepository;
import com.chatAny.chatapi.domain.user.User;
import com.chatAny.chatapi.domain.user.UserRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final RoomRepository roomRepository;

    public UserService(UserRepository userRepository,
                       RoomRepository roomRepository) {
        this.userRepository = userRepository;
        this.roomRepository = roomRepository;
    }

    public User findOrCreateUser(String userName) {
        User existingUser = userRepository.findByName(userName);
        if (existingUser != null) {
            return existingUser;
        } else {
            User user = new User()
                    .setName(userName);
            return userRepository.save(user);
        }
    }

    public ResponseEntity<User> enterRoom(String userId, String roomId) {
        Room room = roomRepository.findById(roomId).orElse(null);
        if (room == null) {
            return ResponseEntity.notFound().build();
        }
        if (room.getNumberOfUsers() >= room.getRoomCapacity()) {
            return ResponseEntity.badRequest().build();
        }
        User user = userRepository.findById(userId).orElse(null);
        if (user != null) {
            room.incrementNumberOfUsers();
            roomRepository.save(room);

            user.setRoomId(roomId);
            userRepository.save(user);
            return ResponseEntity.ok(user);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    public ResponseEntity<User> leaveRoom(String userId, String roomId) {
        Room room = roomRepository.findById(roomId).orElse(null);
        if (room == null) {
            return ResponseEntity.notFound().build();
        }
        User user = userRepository.findById(userId).orElse(null);
        if (user != null && user.getRoomId().equals(roomId)) {
            room.decrementNumberOfUsers();
            roomRepository.save(room);

            user.setRoomId(null);
            userRepository.save(user);
            return ResponseEntity.ok(user);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
