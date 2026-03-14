package com.payoneer.machinecoding.entity;

import jakarta.persistence.*;

@Entity
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String status;

    public Task(){}

    public Long getId() { return id; }

    public String getTitle() { return title; }

    public String getStatus() { return status; }

    public void setId(Long id) { this.id = id; }

    public void setTitle(String title) { this.title = title; }

    public void setStatus(String status) { this.status = status; }
}