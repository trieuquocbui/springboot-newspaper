package com.bqt.newspaer.repository;

import com.bqt.newspaer.entity.Notification;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface NotificationRepository extends MongoRepository<Notification,String> {
}
