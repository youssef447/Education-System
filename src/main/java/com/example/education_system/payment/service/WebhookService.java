package com.example.education_system.payment.service;

import com.example.education_system.config.exceptions.classes.OrderNotFoundException;
import com.example.education_system.order.OrderEntity;
import com.example.education_system.order.OrderRepository;
import com.example.education_system.payment.entity.Currency;
import com.example.education_system.payment.entity.PaymentEntity;
import com.example.education_system.payment.entity.PaymentStatus;
import com.example.education_system.payment.repository.PaymentRepository;
import com.example.education_system.user.entity.UserEntity;
import com.example.education_system.user.service.UserService;
import com.stripe.exception.SignatureVerificationException;
import com.stripe.model.Event;
import com.stripe.model.EventDataObjectDeserializer;
import com.stripe.model.PaymentIntent;
import com.stripe.model.StripeObject;
import com.stripe.model.checkout.Session;
import com.stripe.net.Webhook;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Service
@RequiredArgsConstructor
public class WebhookService {
    private final PaymentRepository paymentRepository;
    private final OrderRepository orderRepository;

    private final UserService UserService;
    @Value("${stripe.webhook.secret}")
    private String endpointSecret;


    @Transactional
    public ResponseEntity<String> handleStripeEvent(
            @RequestBody String payload,
            @RequestHeader("Stripe-Signature") String sigHeader
    ) throws SignatureVerificationException {

        Event event;

        event = Webhook.constructEvent(
                payload, sigHeader, endpointSecret
        );


        if ("payment_intent.succeeded".equals(event.getType())) {
            StripeObject stripeObject;
            EventDataObjectDeserializer dataObjectDeserializer = event.getDataObjectDeserializer();
            if (dataObjectDeserializer.getObject().isPresent()) {
                stripeObject = dataObjectDeserializer.getObject().get();
            } else {
                return ResponseEntity.badRequest().body("Unable to deserialize payment intent from event");

            }
            if (stripeObject instanceof PaymentIntent intent) {
                saveOrder(intent);
                savePayment(intent);
            }

        }

        return ResponseEntity.ok("");
    }

    private void savePayment(PaymentIntent intent) {
        String currency = intent.getCurrency();
        UserEntity user = UserService.getCurrentUser();
        String price = intent.getMetadata().get("price");
        PaymentEntity payment = new PaymentEntity();
        payment.setAmount((BigDecimal.valueOf(Long.parseLong(price))));
        payment.setCurrency(Currency.valueOf(currency.toUpperCase()));
        payment.setStatus(PaymentStatus.SUCCESS);
        payment.setUser(user);
        paymentRepository.save(payment);
    }

    private void saveOrder(PaymentIntent intent) {
        String orderIdStr = intent.getMetadata().get("order_id");
        String price = intent.getMetadata().get("price");
        Long orderId = Long.parseLong(orderIdStr);
        OrderEntity order = getCurrentOrder(orderId);
        order.setStatus(OrderEntity.OrderStatus.PAID);
        order.setTotalPrice(BigDecimal.valueOf(Long.parseLong(price)));
        orderRepository.save(order);

    }


    private OrderEntity getCurrentOrder(Long orderId) {
        return orderRepository.findById(orderId)
                .orElseThrow(OrderNotFoundException::new);
    }

}
