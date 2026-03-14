package com.payoneer.MachineCodingRound.model;

import lombok.Data;
import java.util.Map;

@Data
public class Notification {

    private String id;
    private String templateName;
    private String recipient;
    private Map<String,Object> data;

    private String status;
    private String idempotencyKey;
    private int attempts;
}