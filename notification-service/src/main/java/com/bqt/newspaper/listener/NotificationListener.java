package com.bqt.newspaper.listener;

import com.bqt.newspaper.configuration.RabbitMQConfiguration;
import com.bqt.newspaper.payload.NotificationRequest;
import com.bqt.newspaper.payload.NotificationResponse;
import com.bqt.newspaper.service.INotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class NotificationListener {
    private final INotificationService notificationService;

    @RabbitListener(queues = {RabbitMQConfiguration.CREATED_NOTIFICATION_QUEUE})
    public void createNotification(NotificationRequest notificationRequest){
        NotificationResponse notificationResponse = notificationService.createNotification(notificationRequest);

    }

    @RabbitListener(queues = {RabbitMQConfiguration.NOTIFICATION_CANCELLATION_QUEUE})
    public void deleteNotification(String newspaperId){
        notificationService.deleteNotification(newspaperId);
    }
}
