package com.bqt.newspaer.listener;

import com.bqt.newspaer.configuration.RabbitMQConfiguration;
import com.bqt.newspaer.entity.Newspaper;
import com.bqt.newspaer.payload.TopicOrOriginMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Component;

import static org.springframework.data.mongodb.core.query.Criteria.where;
import static org.springframework.data.mongodb.core.query.Query.query;
import static org.springframework.data.mongodb.core.query.Update.update;


@Component
@RequiredArgsConstructor
public class NewspaperListener {
    private final MongoTemplate mongoTemplate;

    @RabbitListener(queues = {RabbitMQConfiguration.UPDATE_TOPIC_QUEUE})
    public void updateTopicToNewspaper(TopicOrOriginMessage topic){
        mongoTemplate.updateMulti(
                query(where("topic").is(topic.getOldName())),
                update("topic", topic.getNewName()),
                Newspaper.class
        );
    }

    @RabbitListener(queues = {RabbitMQConfiguration.UPDATE_ORIGIN_QUEUE})
    public void updateOriginToNewspaper(TopicOrOriginMessage topic){
        mongoTemplate.updateMulti(
                query(where("origin").is(topic.getOldName())),
                update("origin", topic.getNewName()),
                Newspaper.class
        );
    }
}
