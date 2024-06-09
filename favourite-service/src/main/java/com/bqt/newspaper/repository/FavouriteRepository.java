package com.bqt.newspaper.repository;

import com.bqt.newspaper.entity.Favourite;
import com.bqt.newspaper.entity.Newspaper;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface FavouriteRepository extends MongoRepository<Favourite, String> {
    Optional<Favourite> findByUsernameAndNewspaper(String username, Newspaper newspaperResponse);
}
