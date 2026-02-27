package com.samsung.notification.listener;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import com.samsung.notification.dto.NotificationRequest;
import com.samsung.notification.service.NotificationManagerService;

@Component
public class NotificationEventListener {

    private final NotificationManagerService service;

    public NotificationEventListener(NotificationManagerService service) {
        this.service = service;
    }

    @KafkaListener(topics = "notification-events")
    public void consume(NotificationRequest request) {
        service.sendNotification(request);
    }
}
