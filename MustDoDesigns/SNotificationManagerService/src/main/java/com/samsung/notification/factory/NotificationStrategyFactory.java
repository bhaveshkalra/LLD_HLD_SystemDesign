package com.samsung.notification.factory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.samsung.notification.strategy.*;

@Component
public class NotificationStrategyFactory {

    @Autowired
    private BubbleNotificationStrategy bubble;

    @Autowired
    private HeadsUpNotificationStrategy headsUp;

    @Autowired
    private DetailedPopupStrategy detailed;

    public NotificationStrategy getStrategy(String type) {
        switch(type) {
            case "BUBBLE": return bubble;
            case "HEADSUP": return headsUp;
            case "DETAILED": return detailed;
            default: throw new RuntimeException("Invalid popup style");
        }
    }
}
