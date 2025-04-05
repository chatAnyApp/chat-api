package com.chatAny.chatapi.domain.bus;

import com.chatAny.chatapi.domain.message.Message;

public interface ChatMessageBus {
    void emit(Message message);
}
