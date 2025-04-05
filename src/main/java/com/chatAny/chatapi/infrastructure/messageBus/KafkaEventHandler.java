package com.chatAny.chatapi.infrastructure.messageBus;

import com.chatAny.avro.message.MessageAvro;
import com.chatAny.chatapi.domain.message.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

@Component
public class KafkaEventHandler {

    private final SimpMessagingTemplate template;

    @Autowired
    public KafkaEventHandler(SimpMessagingTemplate template) {
        this.template = template;
    }

    @KafkaListener(topics = "chat")
    public void listen(MessageAvro messageAvro) {
        Message message = new Message()
                .setId(messageAvro.getId().toString())
                .setUserName(messageAvro.getUserName().toString())
                .setUserId(messageAvro.getUserId().toString())
                .setText(messageAvro.getText().toString())
                .setRoomId(messageAvro.getRoomId().toString())
                .setCreatedDate(messageAvro.getCreatedDate());

        String destination = "/chat/" + message.getRoomId();
        template.convertAndSend(destination, message);
    }

}
