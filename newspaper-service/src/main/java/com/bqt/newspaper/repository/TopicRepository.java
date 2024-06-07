package com.bqt.newspaper.repository;

import com.bqt.newspaper.entity.Topic;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface TopicRepository extends MongoRepository<Topic,String> {
    boolean existsByName(String name);

    Optional<Topic> findByName(String name);
}
