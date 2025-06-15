package com.chatAny.chatapi.service.utils;

import com.chatAny.chatapi.domain.message.Message;
import com.chatAny.chatapi.domain.message.MessageRepository;
import com.chatAny.chatapi.domain.room.Room;
import com.chatAny.chatapi.domain.room.RoomRepository;
import org.springframework.messaging.simp.SimpMessagingTemplate;
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
    private final SimpMessagingTemplate simpMessagingTemplate;

    public RoomCleanupService(RoomRepository roomRepository,
                              MessageRepository messageRepository,
                              SimpMessagingTemplate simpMessagingTemplate) {
        this.roomRepository = roomRepository;
        this.messageRepository = messageRepository;
        this.simpMessagingTemplate = simpMessagingTemplate;
    }

    @Async
    @Scheduled(initialDelay = 1, fixedDelay = 30, timeUnit = TimeUnit.MINUTES)
    @Transactional(rollbackFor = Exception.class)
    public void deleteOldRooms() {
        Instant twoHoursAgo = Instant.now().minus(2, ChronoUnit.HOURS);
        var rooms = roomRepository.findByLastModifiedDateLessThan(twoHoursAgo);
        if (rooms != null && !rooms.isEmpty()) {
            var messages = messageRepository.findAllByRoomIdIn(rooms.stream().map(Room::getId).toList());
            if (messages != null && !messages.isEmpty()) {
                messageRepository.deleteAll(messages);
            }
            for (Room room : rooms) {
                simpMessagingTemplate.convertAndSend("/chat/" + room.getId(),
                        new Message()
                                .setText("ROOM_DELETED")
                                .setUserId("SYSTEM")
                                .setRoomId(room.getId()));
            }
            roomRepository.deleteAll(rooms);
        }
    }
}
