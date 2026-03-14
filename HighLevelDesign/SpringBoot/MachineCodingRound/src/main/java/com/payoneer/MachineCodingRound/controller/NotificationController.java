package com.payoneer.MachineCodingRound.controller;

import com.payoneer.MachineCodingRound.model.Notification;
import com.payoneer.MachineCodingRound.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.Map;

@RestController
@RequestMapping("/notifications")
public class NotificationController {

    @Autowired
    NotificationService service;

    @PostMapping("/enqueue") //Enqueue Notifications API End Point
    public Notification enqueue(@RequestBody Notification request) {
        if(request.getTemplateName() == null ||
                request.getRecipient() == null ||
                request.getData() == null ||
                request.getIdempotencyKey() == null) {
            throw new RuntimeException("Invalid request");
        }
        return service.enqueue(request);
    }

    @PostMapping("/process") //Process Pending Notifications API EndPoint
    public Map<String,Object> process(){
        int count = service.processPending();
        return Map.of("processed", count);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> get(@PathVariable String id){
        Notification n = service.getById(id);
        if (n == null) {
            return ResponseEntity.status(404).body("Notification not found");
        }

        return ResponseEntity.ok(n);
    }

    @GetMapping
    public Collection<Notification> getAll() {
        return service.getAll();
    }
}