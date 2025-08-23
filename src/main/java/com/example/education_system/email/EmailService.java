package com.example.education_system.email;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {
    private final JavaMailSender mailSender;

    private final String from;

    public EmailService(JavaMailSender mailSender, @Value("${spring.mail.username}") String from) {
        this.from = from;
        this.mailSender = mailSender;
    }

    @RabbitListener(queues = "${rabbitmq.queue.email}")
    public void sendEmail(EmailMessageDTO emailMessageDTO) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(from);
        message.setTo(emailMessageDTO.to());
        message.setSubject(emailMessageDTO.subject());
        message.setText(emailMessageDTO.body());

        mailSender.send(message);
    }
}
