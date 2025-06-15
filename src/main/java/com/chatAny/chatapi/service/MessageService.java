package com.chatAny.chatapi.service;

import com.chatAny.chatapi.domain.message.Message;
import com.chatAny.chatapi.domain.message.MessageRepository;
import com.chatAny.chatapi.domain.room.Room;
import com.chatAny.chatapi.domain.room.RoomRepository;
import com.chatAny.chatapi.domain.user.User;
import com.chatAny.chatapi.domain.user.UserRepository;
import com.chatAny.chatapi.dto.MessageCreateDto;
import org.springframework.data.domain.Sort;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;

@Service
public class MessageService {
    private final MessageRepository messageRepository;
    private final UserRepository userRepository;
    private final SimpMessagingTemplate template;
    private final RoomRepository roomRepository;

    public MessageService(
            MessageRepository messageRepository,
            UserRepository userRepository,
            SimpMessagingTemplate template,
            RoomRepository roomRepository) {
        this.messageRepository = messageRepository;
        this.userRepository = userRepository;
        this.template = template;
        this.roomRepository = roomRepository;
    }

    public List<Message> getAllMessages() {
        Sort sort = Sort.by(Sort.Order.asc("createdDate"));
        return messageRepository.findAllBy(sort);
    }

    public Message save(MessageCreateDto command) {
        var userId = command.getUserId();
        var roomId = command.getRoomId();
        var text = command.getText();
        User user = userRepository.findByIdAndRoomId(userId, roomId);
        if (user != null) {
            Message message = new Message()
                    .setUserId(userId)
                    .setUserName(user.getName())
                    .setText(text)
                    .setRoomId(roomId);

            Room room = roomRepository.findById(roomId).orElseThrow();
            roomRepository.save(room);

            userRepository.save(user);

            var savedMessage = messageRepository.save(message);
            String destination = "/chat/" + message.getRoomId();
            template.convertAndSend(destination, savedMessage);
            return savedMessage;
        } else {
            throw new IllegalStateException();
        }
    }

    public List<Message> getAllMessagesByRoom(String roomId) {
        Sort sort = Sort.by(Sort.Order.asc("createdDate"));
        return messageRepository.findAllByRoomId(roomId, sort);
    }
}
