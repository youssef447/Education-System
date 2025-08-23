package com.example.education_system.config.broker;

import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;

@RequiredArgsConstructor
public abstract class BaseProducer {

    private final RabbitTemplate rabbitTemplate;
    @Value("${rabbitmq.exchange.name}")
    private String exchangeName;

    protected void send(String routingKey, Object payload) {
        rabbitTemplate.convertAndSend(exchangeName, routingKey, payload);
    }
}
