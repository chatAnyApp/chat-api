package com.chatAny.chatapi.service.utils;

import com.chatAny.chatapi.domain.room.Room;
import com.chatAny.chatapi.domain.room.RoomRepository;
import com.chatAny.chatapi.domain.user.UserRepository;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.concurrent.TimeUnit;

@Service
public class UserCleanupService {

    private final UserRepository userRepository;
    private final RoomRepository roomRepository;

    public UserCleanupService(UserRepository userRepository, RoomRepository roomRepository) {
        this.userRepository = userRepository;
        this.roomRepository = roomRepository;
    }

    @Async
    @Scheduled(initialDelay = 5, fixedDelay = 30, timeUnit = TimeUnit.MINUTES)
    @Transactional(rollbackFor = Exception.class)
    public void deleteOldUsers() {
        Instant twoHoursAgo = Instant.now().minus(4, ChronoUnit.HOURS);
        var users = userRepository.findByLastModifiedDateLessThan(twoHoursAgo);
        if (users.isEmpty()) {
            return;
        }
        for (var user : users) {
            if (user.getRoomId() != null) {
                roomRepository.findById(user.getRoomId()).ifPresent(Room::decrementNumberOfUsers);
            }
            userRepository.delete(user);
        }
    }

}
