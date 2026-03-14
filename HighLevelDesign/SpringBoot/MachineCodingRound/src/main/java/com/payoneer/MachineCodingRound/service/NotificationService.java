package com.payoneer.MachineCodingRound.service;

import com.payoneer.MachineCodingRound.model.Notification;
import com.payoneer.MachineCodingRound.model.Template;
import com.payoneer.MachineCodingRound.repository.NotificationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.UUID;

@Service
public class NotificationService {
    @Autowired
    NotificationRepository notificationRepository;

    @Autowired
    TemplateService templateService;

    public Notification enqueue(Notification request) {
        // idempotency check
        Notification existing = notificationRepository.findByIdempotencyKey(request.getIdempotencyKey());

        if (existing != null){
            return existing;
        }

        // template validation
        Template template = templateService.getByName(request.getTemplateName());

        if (template == null) {
            throw new RuntimeException("Template not found");
        }

        request.setId(UUID.randomUUID().toString());
        request.setStatus("PENDING");
        request.setAttempts(0);
        return notificationRepository.save(request);
    }

    public int processPending() {
        int count = 0;
        for (Notification n : notificationRepository.findAll()) {
            if ("PENDING".equals(n.getStatus())) {
                System.out.println("Sending notification to " + n.getRecipient());

                n.setStatus("SENT");
                n.setAttempts(n.getAttempts() + 1);

                count++;
            }
        }
        return count;
    }

    public Notification getById(String id){
        return notificationRepository.findById(id);
    }

    public Collection<Notification> getAll(){
        return notificationRepository.findAll();
    }
}