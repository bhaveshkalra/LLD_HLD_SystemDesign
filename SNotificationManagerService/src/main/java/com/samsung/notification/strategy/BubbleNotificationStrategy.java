package com.samsung.notification.strategy;

import org.springframework.stereotype.Component;

@Component
public class BubbleNotificationStrategy implements NotificationStrategy {
    @Override
    public void showPopup(String message) {
        System.out.println("Bubble Popup: " + message);
    }
}
