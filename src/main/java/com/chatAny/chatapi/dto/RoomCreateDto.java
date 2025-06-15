package com.chatAny.chatapi.dto;

import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class RoomCreateDto {
    private String userId;

    @Size(min = 1, max = 32)
    private String roomName;

}
