package com.bqt.newspaper.configuration;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfiguration {

    public static final String DELETE_FILE_DIRECT_EXCHANGE = "delete-file-direct-exchange";
    public static final String DELETE_FILE_QUEUE = "delete-file-queue";
    public static final String ROUTING_KEY_DELETE_FILE  = "delete-file";

    @Bean
    public DirectExchange deleteFileDirectExchange(){return new DirectExchange(DELETE_FILE_DIRECT_EXCHANGE);}

    @Bean
    public Queue deleteFileQueue(){
        return new Queue(DELETE_FILE_QUEUE);
    }

    @Bean
    public Binding deleteFileBinding(){
        return BindingBuilder.bind(deleteFileQueue()).to(deleteFileDirectExchange()).with(ROUTING_KEY_DELETE_FILE);
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
