package com.bqt.newspaer.event;

import com.bqt.newspaer.configuration.RabbitMQConfiguration;
import com.bqt.newspaer.payload.NotificationRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class NotificationEvent {

    private final RabbitTemplate rabbitTemplate;

    public void createNotification(NotificationRequest  notificationRequest){
        rabbitTemplate.convertAndSend(RabbitMQConfiguration.NOTIFICATION_DIRECT_EXCHANGE,
                RabbitMQConfiguration.ROUTING_KEY_NOTIFICATION,
                notificationRequest);
    }

}
