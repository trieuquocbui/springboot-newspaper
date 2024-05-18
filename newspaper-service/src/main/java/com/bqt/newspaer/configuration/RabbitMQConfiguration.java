package com.bqt.newspaer.configuration;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfiguration {

    public static final String NOTIFICATION_DIRECT_EXCHANGE = "notification-direct-exchange";
    public static final String UPDATE_NEWSPAPER_DIRECT_EXCHANGE = "update-topic-direct-exchange";
    public static final String DELETE_FILE_DIRECT_EXCHANGE = "delete-file-direct-exchange";
    public static final String UPDATE_TOPIC_QUEUE  = "update-topic-queue";
    public static final String UPDATE_ORIGIN_QUEUE  = "update-origin-queue";
    public static final String CREATED_NOTIFICATION_QUEUE = "created-notification-queue";
    public static final String DELETE_FILE_QUEUE = "delete-file-queue";
    public static final String ROUTING_KEY_NOTIFICATION = "created-notification";
    public static final String ROUTING_KEY_UPDATE_TOPIC  = "update-topic";
    public static final String ROUTING_KEY_UPDATE_ORIGIN  = "update-origin";
    public static final String ROUTING_KEY_DELETE_FILE  = "delete-file";

    @Bean
    public DirectExchange updateTopicDirectExchange(){return new DirectExchange(UPDATE_NEWSPAPER_DIRECT_EXCHANGE);}
    @Bean
    public DirectExchange updateOriginDirectExchange(){return new DirectExchange(UPDATE_NEWSPAPER_DIRECT_EXCHANGE);}
    @Bean
    public DirectExchange notificationDirectExchange(){
        return new DirectExchange(NOTIFICATION_DIRECT_EXCHANGE);
    }
    @Bean
    public DirectExchange deleteFileDirectExchange(){return new DirectExchange(DELETE_FILE_DIRECT_EXCHANGE);}

    @Bean
    public Queue updateTopicQueue(){
        return new Queue(UPDATE_TOPIC_QUEUE);
    }
    @Bean
    public Queue updateOriginQueue(){
        return new Queue(UPDATE_ORIGIN_QUEUE);
    }
    @Bean
    public Queue createNotificationQueue(){
        return new Queue(CREATED_NOTIFICATION_QUEUE);
    }
    @Bean
    public Queue deleteFileQueue(){
        return new Queue(DELETE_FILE_QUEUE);
    }

    @Bean
    public Binding deleteFileBinding(){
        return BindingBuilder.bind(deleteFileQueue()).to(deleteFileDirectExchange()).with(ROUTING_KEY_DELETE_FILE);
    }
    @Bean
    public Binding updateTopicBinding() {
        return BindingBuilder.bind(updateTopicQueue()).to(updateTopicDirectExchange()).with(ROUTING_KEY_UPDATE_TOPIC);
    }
    @Bean
    public Binding updateOriginBinding() {
        return BindingBuilder.bind(updateOriginQueue()).to(updateOriginDirectExchange()).with(ROUTING_KEY_UPDATE_ORIGIN);
    }
    @Bean
    public Binding notificationBinding() {
        return BindingBuilder.bind(createNotificationQueue()).to(notificationDirectExchange()).with(ROUTING_KEY_NOTIFICATION);
    }

    @Bean
    public MessageConverter jsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }


    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
        final RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(jsonMessageConverter());
        return rabbitTemplate;
    }

}
