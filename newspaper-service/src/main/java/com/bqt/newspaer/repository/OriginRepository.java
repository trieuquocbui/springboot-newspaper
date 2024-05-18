package com.bqt.newspaer.repository;

import com.bqt.newspaer.entity.Origin;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface OriginRepository extends MongoRepository<Origin,String> {
    boolean existsByName(String name);

    Optional<Origin> findByName(String name);
}
