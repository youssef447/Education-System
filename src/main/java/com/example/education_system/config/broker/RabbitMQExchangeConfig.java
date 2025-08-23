package com.example.education_system.config.broker;

import org.springframework.amqp.core.DirectExchange;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQExchangeConfig {

    @Value("${rabbitmq.exchange.name}")
    private String exchangeName;

    @Bean
    public DirectExchange appExchange() {
        return new DirectExchange(exchangeName, true, false);
    }

}