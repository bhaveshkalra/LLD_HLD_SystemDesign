package com.samsung.notification;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class NotificationApplication {
    public static void main(String[] args) {
        SpringApplication.run(NotificationApplication.class, args);
    }
}






















/*
1️⃣ Problem Statement
“Design a Notification Manager Service where
users configure their preferred popup style — Bubble, Heads-Up, or Detailed.
When a notification event arrives, the system dynamically selects the correct popup strategy based on user preferences.”

A notification manager service using Strategy and Factory patterns.
User preferences determine whether Bubble, HeadsUp, or Detailed popup style
is used. The controller receives notification events,
the service retrieves user preferences, and a strategy factory selects the correct popup implementation dynamically.
This design is extensible, testable, and aligns with the modular popup architecture in Samsung OneUI.”

Bubble → Floating UI
HeadsUp → Top banner
Detailed → Full popup

2️⃣ High-Level Architecture

Client / System Event
        |
NotificationController
        |
NotificationService
        |
NotificationStrategyFactory
        |
+-----------------------------+
| BubbleStrategy              |
| HeadsUpStrategy             |
| DetailedPopupStrategy       |
+-----------------------------+
        |
PreferenceRepository (DB)

1️⃣ API DESIGN (REST Endpoints)

Preference Management APIs
Notification Trigger APIs

🔹 Preference APIs
✅ Save / Update Preference
POST /api/v1/preferences

Request JSON
{
  "userId": "bhavesh123",
  "popupStyle": "HEADSUP",
  "enabled": true
}

Response
{
  "status": "SUCCESS",
  "message": "Preference saved"
}

✅ Get User Preference
GET /api/v1/preferences/{userId}

Response
{
  "userId": "bhavesh123",
  "popupStyle": "HEADSUP",
  "enabled": true
}

🔹 Notification API
Trigger Notification
POST /api/v1/notifications/send

Request JSON
{
  "userId": "bhavesh123",
  "title": "New Message",
  "message": "You received a message",
  "priority": "HIGH"
}

Response
{
  "status": "DELIVERED",
  "popupStyle": "HEADSUP"
}
*/

/**
 🚀 1️⃣3️⃣ Scalability Improvements

 Add Cache
 Redis → cache user preference

 Add Async Processing
 Kafka → NotificationEvent → Consumer

 Add Rate Limiter
 Prevent notification spam.

 🧠 1️⃣4️⃣ SOLID Principles Mapping
 Principle	Applied
 SRP	Each strategy handles only one popup
 OCP	Add new popup style easily
 LSP	All strategies interchangeable
 DIP	Service depends on interface
 */


