package com.chatAny.chatapi.api;

import com.chatAny.chatapi.dto.MessageCreateDto;
import com.chatAny.chatapi.service.MessageService;
import com.chatAny.chatapi.domain.message.Message;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/messages")
public class MessageController {
    private final MessageService messageService;

    public MessageController(
            MessageService messageService) {
        this.messageService = messageService;
    }

    @GetMapping()
    public List<Message> getMessages() throws Exception {
        return messageService.getAllMessages();
    }

    @GetMapping("room/{roomId}")
    public List<Message> getMessagesByRoom(@PathVariable String roomId) throws Exception {
        return messageService.getAllMessagesByRoom(roomId);
    }

    @PostMapping("new")
    public void addNewMessage(
            @Valid @RequestBody MessageCreateDto command) {
        try {
            messageService.save(command);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
