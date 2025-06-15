package com.chatAny.chatapi.domain.user;

import com.chatAny.chatapi.domain.room.Room;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.List;

@Repository
public interface UserRepository extends MongoRepository<User, String> {
    User findByName(String name);

    User findByIdAndRoomId(String id, String roomId);

    List<User> findByLastModifiedDateLessThan(Instant lastModifiedDate);

}
