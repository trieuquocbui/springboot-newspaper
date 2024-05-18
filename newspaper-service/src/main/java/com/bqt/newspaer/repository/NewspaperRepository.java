package com.bqt.newspaer.repository;

import com.bqt.newspaer.entity.Newspaper;
import com.bqt.newspaer.entity.Origin;
import com.bqt.newspaer.entity.Topic;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Date;
import java.util.List;

public interface NewspaperRepository extends MongoRepository<Newspaper,String> {
    List<Newspaper> findByTopic(String topic);
    List<Newspaper> findByOrigin(String topic);

    boolean existsByName(String name);

    Page<Newspaper> findByDatetimeBetween(Date startDate, Date endDate, Pageable pageable);

    List<Newspaper> findByDatetimeBetween(Date startDate, Date endDate);
}
