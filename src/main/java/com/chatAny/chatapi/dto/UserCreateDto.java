package com.chatAny.chatapi.dto;


import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class UserCreateDto {
    @NotNull
    private String name;
}
