package com.bqt.newspaper.event;

import com.bqt.newspaper.configuration.RabbitMQConfiguration;
import com.bqt.newspaper.payload.NotificationRequest;
import lombok.RequiredArgsConstructor;
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


    public void deleteNotification(String newspaperId){
        rabbitTemplate.convertAndSend(RabbitMQConfiguration.NOTIFICATION_CANCELLATION_DIRECT_EXCHANGE,
                RabbitMQConfiguration.ROUTING_KEY_NOTIFICATION_CANCELLATION,
                newspaperId);
    }

}
