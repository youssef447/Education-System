package com.example.education_system.email;

import com.example.education_system.config.broker.BaseProducer;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class EmailProducer extends BaseProducer {

    private final String routingEmail;

    public EmailProducer(RabbitTemplate rabbitTemplate, @Value("${rabbitmq.routing.email}") String route) {
        super(rabbitTemplate);
        routingEmail = route;
    }

    public void sendEmail(Object payload) {
        send(routingEmail, payload);
    }
}