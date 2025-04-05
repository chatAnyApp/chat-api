package com.chatAny.chatapi.repository.user;

import com.chatAny.chatapi.domain.user.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DefaultMongoUserRepository extends MongoRepository<User, String> {
//    User findByName(String name);
//
//    Optional<User> findById(String id);
}
