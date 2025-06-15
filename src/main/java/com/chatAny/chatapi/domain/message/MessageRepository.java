package com.chatAny.chatapi.domain.message;

import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MessageRepository extends MongoRepository<Message, String> {

    List<Message> findAllBy(Sort sort);

    List<Message> findAllByRoomId(String roomId, Sort sort);

    List<Message> findAllByRoomIdIn(List<String> roomIds);
}
