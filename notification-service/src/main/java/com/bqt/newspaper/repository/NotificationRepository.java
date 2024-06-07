package com.bqt.newspaper.repository;

import com.bqt.newspaper.entity.Notification;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface NotificationRepository extends MongoRepository<Notification,String> {
    Optional<Notification> findByNewspaperId(String newspaperId);
}
