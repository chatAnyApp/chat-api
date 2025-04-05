package com.chatAny.chatapi.domain.user;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends MongoRepository<User, String> {
    User findByName(String name);

    User findByIdAndRoomId(String id, String roomId);
}
