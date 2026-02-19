package com.samsung.notification.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.samsung.notification.dto.NotificationRequest;
import com.samsung.notification.service.NotificationManagerService;

@RestController
@RequestMapping("/api/v1/notifications")
public class NotificationController {

    private final NotificationManagerService service;

    public NotificationController(NotificationManagerService service) {
        this.service = service;
    }

    @PostMapping("/send")
    public ResponseEntity<String> send(@RequestBody NotificationRequest request) {
        service.sendNotification(request);
        return ResponseEntity.ok("Notification Delivered");
    }
}
