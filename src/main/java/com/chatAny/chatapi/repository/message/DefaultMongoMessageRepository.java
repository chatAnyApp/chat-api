package com.chatAny.chatapi.repository.message;

import com.chatAny.chatapi.domain.message.Message;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DefaultMongoMessageRepository extends MongoRepository<Message, String> {
}
