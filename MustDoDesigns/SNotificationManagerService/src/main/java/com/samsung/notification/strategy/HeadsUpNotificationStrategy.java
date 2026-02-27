package com.samsung.notification.strategy;

import org.springframework.stereotype.Component;

@Component
public class HeadsUpNotificationStrategy implements NotificationStrategy {
    @Override
    public void showPopup(String message) {
        System.out.println("HeadsUp Popup: " + message);
    }
}
