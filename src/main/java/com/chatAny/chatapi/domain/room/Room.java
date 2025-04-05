package com.chatAny.chatapi.domain.room;

import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.data.annotation.Id;

import java.io.Serializable;
import java.time.Instant;

@Data
@Accessors(chain = true)
public class Room implements Serializable {

    @Id
    private String id;
    private String name;
    private Instant createdDate;
    private Integer numberOfUsers = 0;
    private Integer roomCapacity = 5;

    public void incrementNumberOfUsers() {
        if (this.numberOfUsers < roomCapacity)
            this.numberOfUsers++;
    }

    public void decrementNumberOfUsers() {
        if (this.numberOfUsers > 0) this.numberOfUsers--;
    }
}
