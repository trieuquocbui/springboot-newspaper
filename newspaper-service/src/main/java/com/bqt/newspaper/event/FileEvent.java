package com.bqt.newspaper.event;

import com.bqt.newspaper.configuration.RabbitMQConfiguration;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class FileEvent {
    private final RabbitTemplate rabbitTemplate;

    public void deleteFile(List<String> fileNames){
        rabbitTemplate.convertAndSend(RabbitMQConfiguration.DELETE_FILE_DIRECT_EXCHANGE,
                RabbitMQConfiguration.ROUTING_KEY_DELETE_FILE,
                fileNames);
    }
}
