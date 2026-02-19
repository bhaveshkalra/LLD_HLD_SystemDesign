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
*/
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.samsung.notification.dto.NotificationRequest;
import com.samsung.notification.service.NotificationManagerService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.samsung.notification.dto.NotificationRequest;
import com.samsung.notification.entity.UserPreference;
import com.samsung.notification.repository.PreferenceRepository;
import com.samsung.notification.factory.NotificationStrategyFactory;
import com.samsung.notification.strategy.NotificationStrategy;
import org.springframework.stereotype.Component;

import com.samsung.notification.strategy.NotificationStrategy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.samsung.notification.strategy.BubbleNotificationStrategy;
import com.samsung.notification.strategy.HeadsUpNotificationStrategy;
import com.samsung.notification.strategy.DetailedPopupStrategy;
import com.samsung.notification.strategy.NotificationStrategy;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Version;

import com.samsung.notification.enums.PopupStyle;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.samsung.notification.entity.UserPreference;
import lombok.Data;





@Entity
@Table(name = "user_preferences")
public class UserPreference {

    @Id
    private String userId;

    @NotNull
    @Pattern(regexp="BUBBLE|HEADSUP|DETAILED")
    private String popupStyle;

    private boolean enabled;

    @Version
    private Long version;   // optimistic locking
}

class PreferenceRequest {
    private String userId;
    private String popupStyle;
    private boolean enabled;
}

class NotificationRequest {
    private String userId;
    private String title;
    private String message;
    private String priority;
}

@Repository
interface PreferenceRepository 
       extends JpaRepository<UserPreference, String> {
}


interface NotificationStrategy {
    void showPopup(String message);
}


@Component
class BubbleNotificationStrategy 
       implements NotificationStrategy {

    @Override
    public void showPopup(String message) {
        System.out.println("Bubble Popup: " + message);
    }
}

@Component
class HeadsUpNotificationStrategy 
       implements NotificationStrategy {

    @Override
    public void showPopup(String message) {
        System.out.println("HeadsUp Popup: " + message);
    }
}

@Component
class DetailedPopupStrategy 
       implements NotificationStrategy {

    @Override
    public void showPopup(String message) {
        System.out.println("Detailed Popup: " + message);
    }
}

@Component
class NotificationStrategyFactory {

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
            default: throw new RuntimeException("Invalid style");
        }
    }
}


/*🔟 Service Layer (Business Logic) */


@Service
public class SNotificationManagerService {

    private final PreferenceRepository repository;
    private final NotificationStrategyFactory factory;

    public SNotificationManagerService(PreferenceRepository repo,
                               NotificationStrategyFactory factory) {
        this.repository = repo;
        this.factory = factory;
    }

    public void sendNotification(NotificationRequest request) {

        UserPreference pref =
            repository.findById(request.getUserId())
                      .orElseThrow();

        NotificationStrategy strategy =
            factory.getStrategy(pref.getPopupStyle());

        strategy.showPopup(request.getMessage());
    }
}

@RestController
@RequestMapping("/api/v1/notifications")
class NotificationController {

    private final SNotificationManagerService service;

    public NotificationController(SNotificationManagerService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<?> notifyUser(
        @RequestBody NotificationRequest request) {

        service.sendNotification(request);
        return ResponseEntity.ok("Sent");
    }
}

@Service
public class PreferenceService {

    private final PreferenceRepository repo;

    public PreferenceService(PreferenceRepository repo) {
        this.repo = repo;
    }

    @Transactional
    public void savePreference(PreferenceRequest req) {

        UserPreference pref =
            repo.findById(req.getUserId())
                .orElse(new UserPreference());

        pref.setUserId(req.getUserId());
        pref.setPopupStyle(
            PopupStyle.valueOf(req.getPopupStyle()));
        pref.setEnabled(req.isEnabled());

        repo.save(pref);
    }
}


@RestController
@RequestMapping("/api/v1/preferences")
public class PreferenceController {

    private final PreferenceService service;

    @PostMapping
    public ResponseEntity<?> savePreference(
            @RequestBody PreferenceRequest request) {

        service.savePreference(request);
        return ResponseEntity.ok("Saved");
    }

    @GetMapping("/{userId}")
    public UserPreference getPref(@PathVariable String userId) {
        return service.getPreference(userId);
    }
}

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<?> handleUserNotFound() {

        return ResponseEntity.status(404)
                .body(Map.of(
                    "error", "USER_NOT_FOUND",
                    "message", "Preference not found"));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handleGeneric() {

        return ResponseEntity.status(500)
                .body(Map.of(
                    "error", "INTERNAL_ERROR"));
    }
}

/*
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

