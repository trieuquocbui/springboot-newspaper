package com.bqt.newspaper.listener;

import com.bqt.newspaper.configuration.RabbitMQConfiguration;
import com.bqt.newspaper.service.IFileService;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;

@Component
@RequiredArgsConstructor
public class FileListener {

    @Value("${spring.resources.static-locations}")
    private String path;

    private final IFileService fileService;

    @RabbitListener(queues = {RabbitMQConfiguration.DELETE_FILE_QUEUE})
    public void updateTopicToNewspaper(List<String> fileNames) throws IOException {
        for (String fileName: fileNames){
            this.fileService.deleteImage(path,fileName);
        }
    }
}
