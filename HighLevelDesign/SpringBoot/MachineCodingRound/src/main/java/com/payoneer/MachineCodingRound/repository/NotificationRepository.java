package com.payoneer.MachineCodingRound.repository;

import com.payoneer.MachineCodingRound.model.Notification;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class NotificationRepository {

    private Map<String, Notification> notifications = new ConcurrentHashMap<>();
    private Map<String, Notification> idempotencyMap = new ConcurrentHashMap<>();

    public Notification save(Notification n){
        notifications.put(n.getId(), n);
        idempotencyMap.put(n.getIdempotencyKey(), n);
        return n;
    }

    public Notification findById(String id){
        return notifications.get(id);
    }

    public Notification findByIdempotencyKey(String key){
        return idempotencyMap.get(key);
    }

    public Collection<Notification> findAll(){
        return notifications.values();
    }
}
