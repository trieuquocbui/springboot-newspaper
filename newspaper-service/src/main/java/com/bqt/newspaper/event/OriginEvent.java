package com.bqt.newspaper.event;

import com.bqt.newspaper.configuration.RabbitMQConfiguration;
import com.bqt.newspaper.payload.TopicOrOriginMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class OriginEvent {
    private final RabbitTemplate rabbitTemplate;

    public void updateOriginToNewspapers(TopicOrOriginMessage origin){
        rabbitTemplate.convertAndSend(RabbitMQConfiguration.UPDATE_NEWSPAPER_DIRECT_EXCHANGE,
                RabbitMQConfiguration.ROUTING_KEY_UPDATE_ORIGIN,
                origin);
    }
}
