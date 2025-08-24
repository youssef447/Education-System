package com.example.education_system.payment.broker;

import com.example.education_system.config.exceptions.classes.OrderNotFoundException;
import com.example.education_system.enrollment.entity.EnrollmentEntity;
import com.example.education_system.enrollment.repository.EnrollmentRepository;
import com.example.education_system.order.OrderEntity;
import com.example.education_system.order.OrderRepository;
import com.example.education_system.payment.entity.Currency;
import com.example.education_system.payment.entity.PaymentEntity;
import com.example.education_system.payment.entity.PaymentStatus;
import com.example.education_system.payment.repository.PaymentRepository;
import com.example.education_system.user.entity.UserEntity;
import com.example.education_system.user.service.UserService;
import com.stripe.model.PaymentIntent;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class PaymentConsumer {
    private final UserService userService;

    private final PaymentRepository paymentRepository;
    private final OrderRepository orderRepository;
    private final EnrollmentRepository enrollmentRepository;

    @RabbitListener(queues = "${rabbitmq.queue.payment}")
    @Transactional
    public void consume(PaymentIntent intent) {
        saveOrder(intent);
        savePayment(intent);
        enrollStudent(intent);
    }

    private void savePayment(PaymentIntent intent) {
        String currency = intent.getCurrency();
        UserEntity user = userService.getCurrentUser();
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

    private void enrollStudent(PaymentIntent intent) {
        UserEntity user = userService.getCurrentUser();
        Long orderId = Long.parseLong(intent.getMetadata().get("order_id"));
        OrderEntity order = getCurrentOrder(orderId);

        order.getItems().forEach(item -> {
            EnrollmentEntity enrollment = EnrollmentEntity.builder()
                    .student(user)
                    .course(item.getCourse())
                    .build();

            enrollmentRepository.save(enrollment);
        });
    }


    private OrderEntity getCurrentOrder(Long orderId) {
        return orderRepository.findById(orderId)
                .orElseThrow(OrderNotFoundException::new);
    }

}

