package com.chatAny.chatapi.service;

import com.chatAny.chatapi.domain.bus.ChatMessageBus;
import com.chatAny.chatapi.domain.message.Message;
import com.chatAny.chatapi.domain.message.MessageRepository;
import com.chatAny.chatapi.domain.user.User;
import com.chatAny.chatapi.domain.user.UserRepository;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;

@Service
public class MessageService {
    private final MessageRepository messageRepository;
    private final UserRepository userRepository;
    private final ChatMessageBus chatMessageBus;

    public MessageService(
            MessageRepository messageRepository,
            UserRepository userRepository,
            ChatMessageBus chatMessageBus
    ) {
        this.messageRepository = messageRepository;
        this.userRepository = userRepository;
        this.chatMessageBus = chatMessageBus;
    }

    public List<Message> getAllMessages() {
        Sort sort = Sort.by(Sort.Order.asc("createdDate"));
        return messageRepository.findAllBy(sort);
    }

    public Message save(String userId,
                        String text,
                        String roomId) {
        User user = userRepository.findByIdAndRoomId(userId, roomId);
        if (user != null) {
            Message message = new Message()
                    .setUserId(userId)
                    .setUserName(user.getName())
                    .setText(text)
                    .setRoomId(roomId)
                    .setCreatedDate(Instant.now());
            return messageRepository.save(message);
        } else {
            throw new IllegalStateException();
        }
    }

    public void send(Message message) {
        chatMessageBus.emit(message);
    }

    public List<Message> getAllMessagesByRoom(String roomId) {
        Sort sort = Sort.by(Sort.Order.asc("createdDate"));
        return messageRepository.findAllByRoomId(roomId, sort);
    }
}
