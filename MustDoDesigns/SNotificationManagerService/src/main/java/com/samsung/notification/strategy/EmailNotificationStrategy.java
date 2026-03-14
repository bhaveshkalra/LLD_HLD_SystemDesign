package com.samsung.notification.strategy;

import org.springframework.stereotype.Component;

@Component
public class EmailNotificationStrategy implements NotificationStrategy {
    @Override
    public void showPopup(String message) {
        System.out.println("Email sent");
    }
}
