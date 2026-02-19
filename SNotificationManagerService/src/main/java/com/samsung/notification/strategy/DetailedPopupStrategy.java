package com.samsung.notification.strategy;

import org.springframework.stereotype.Component;

@Component
public class DetailedPopupStrategy implements NotificationStrategy {
    @Override
    public void showPopup(String message) {
        System.out.println("Detailed Popup: " + message);
    }
}
