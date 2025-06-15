package com.chatAny.chatapi.domain.room;

import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.List;

@Repository
public interface RoomRepository extends MongoRepository<Room, String> {
    List<Room> findAllBy(Sort sort);

    Room findByName(String roomName);

    // find lastModifiedDate less than a given Instant
    List<Room> findByLastModifiedDateLessThan(Instant lastModifiedDate);
}
