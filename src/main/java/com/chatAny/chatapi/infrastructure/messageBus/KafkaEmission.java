package com.chatAny.chatapi.infrastructure.messageBus;

import com.chatAny.chatapi.domain.bus.ChatMessageBus;
import com.chatAny.chatapi.domain.message.Message;
import com.chatAny.avro.message.MessageAvro;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class KafkaEmission implements ChatMessageBus {

    private final KafkaTemplate<String, MessageAvro> kafkaTemplate;

    @Autowired
    public KafkaEmission(KafkaTemplate<String, MessageAvro> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    @Override
    public void emit(Message message) {
        MessageAvro messageAvro = new MessageAvro(
                message.getId(),
                message.getText(),
                message.getUserId(),
                message.getUserName(),
                message.getRoomId(),
                message.getCreatedDate()
        );

        kafkaTemplate.send("chat", messageAvro);
    }
}
