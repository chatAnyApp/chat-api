package com.chatAny.chatapi.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class MessageCreateDto {
    @NotNull
    private String text;
    @NotNull
    private String userId;
    @NotNull
    private String roomId;
}
