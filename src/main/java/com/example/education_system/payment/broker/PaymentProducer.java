package com.example.education_system.payment.broker;

import com.example.education_system.config.broker.BaseProducer;
import com.stripe.model.PaymentIntent;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service

public class PaymentProducer extends BaseProducer {

    private final String routingPayment;

    public PaymentProducer(RabbitTemplate rabbitTemplate, @Value("${rabbitmq.routing.payment}") String route) {
        super(rabbitTemplate);
        routingPayment = route;
    }

    public void postPay(PaymentIntent payload) {
        send(routingPayment, payload);
    }
}
