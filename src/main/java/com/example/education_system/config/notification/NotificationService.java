package com.example.education_system.config.notification;

import lombok.RequiredArgsConstructor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class NotificationService {

    private final SimpMessagingTemplate messagingTemplate;

    public void sendNotification(String username, String message, NotificationType notificationType) {
        Map<String, String> payload = new HashMap<>();
        payload.put("content", message);

        messagingTemplate.convertAndSendToUser(username, "/queue/notifications", payload);
    }
}
