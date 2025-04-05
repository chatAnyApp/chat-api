package com.chatAny.chatapi.domain.user;

import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.data.annotation.Id;

import java.io.Serializable;
import java.time.Instant;

@Data
@Accessors(chain = true)
public class User implements Serializable {

    @Id
    private String id;

    private String name;
    private String roomId;
    private Instant createdDate;
}
