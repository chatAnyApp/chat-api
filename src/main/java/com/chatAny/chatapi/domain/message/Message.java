package com.chatAny.chatapi.domain.message;

import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;

import java.io.Serializable;
import java.time.Instant;

@Data
@Accessors(chain = true)
public class Message implements Serializable {

    @Id
    private String id;

    private String userName;
    private String userId;
    private String text;

    @CreatedDate
    private Instant createdDate;
    private String roomId;
}
