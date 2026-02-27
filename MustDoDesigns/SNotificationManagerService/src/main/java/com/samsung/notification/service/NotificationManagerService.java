package com.samsung.notification.service;

import org.springframework.stereotype.Service;

import com.samsung.notification.dto.NotificationRequest;
import com.samsung.notification.entity.UserPreference;
import com.samsung.notification.factory.NotificationStrategyFactory;
import com.samsung.notification.repository.PreferenceRepository;
import com.samsung.notification.strategy.NotificationStrategy;

@Service
public class NotificationManagerService {

    private final PreferenceRepository repository;
    private final NotificationStrategyFactory factory;

    public NotificationManagerService(PreferenceRepository repository,
                                      NotificationStrategyFactory factory) {
        this.repository = repository;
        this.factory = factory;
    }

    public void sendNotification(NotificationRequest req) {

        UserPreference pref = repository.findById(req.getUserId())
                .orElseThrow(() -> new RuntimeException("User preference not found"));

        NotificationStrategy strategy =
                factory.getStrategy(pref.getPopupStyle().name());

        strategy.showPopup(req.getMessage());
    }
}
