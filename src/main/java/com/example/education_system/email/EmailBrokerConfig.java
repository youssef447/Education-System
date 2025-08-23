package com.example.education_system.email;

import lombok.RequiredArgsConstructor;
import org.springframework.amqp.core.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor

public class EmailBrokerConfig {
    @Value("rabbitmq.routing.email")
    private String emailRoute;
    @Value("rabbitmq.queue.email")
    private String emailQueue;

    @Bean
    public Queue emailQueue() {
        return new Queue(emailQueue, true);
    }

    @Bean
    public Binding bindEmailQueue(Queue emailQueue, DirectExchange appExchange) {
        return BindingBuilder.bind(emailQueue)
                .to(appExchange)
                .with(emailRoute);
    }

}


