package com.bqt.newspaer.listener;

import com.bqt.newspaer.configuration.RabbitMQConfiguration;
import com.bqt.newspaer.payload.NotificationRequest;
import com.bqt.newspaer.payload.NotificationResponse;
import com.bqt.newspaer.service.INotificationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class NotificationListener {
    private final INotificationService notificationService;

    @RabbitListener(queues = {RabbitMQConfiguration.CREATED_NOTIFICATION_QUEUE})
    public void createNotification(NotificationRequest notificationRequest){
        NotificationResponse notificationResponse = notificationService.createNotification(notificationRequest);

    }
}
