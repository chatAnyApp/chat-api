package com.chatAny.chatapi.service.utils;

import com.chatAny.chatapi.domain.message.MessageRepository;
import com.chatAny.chatapi.domain.room.Room;
import com.chatAny.chatapi.domain.room.RoomRepository;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.concurrent.TimeUnit;

@Service
public class RoomCleanupService {

    private final MessageRepository messageRepository;
    private final RoomRepository roomRepository;

    public RoomCleanupService(RoomRepository roomRepository, MessageRepository messageRepository) {
        this.roomRepository = roomRepository;
        this.messageRepository = messageRepository;
    }

    @Async
    @Scheduled(initialDelay = 0, fixedDelay = 1, timeUnit = TimeUnit.HOURS)
    @Transactional(rollbackFor = Exception.class)
    public void deleteOldRooms() {
        Instant twoHoursAgo = Instant.now().minus(2, ChronoUnit.HOURS);
        var rooms = roomRepository.findByLastModifiedDateLessThan(twoHoursAgo);
        if (rooms != null && !rooms.isEmpty()) {
            var messages = messageRepository.findAllByRoomIdIn(rooms.stream().map(Room::getId).toList());
            if (messages != null && !messages.isEmpty()) {
                messageRepository.deleteAll(messages);
            }
            roomRepository.deleteAll(rooms);
        }
    }
}
