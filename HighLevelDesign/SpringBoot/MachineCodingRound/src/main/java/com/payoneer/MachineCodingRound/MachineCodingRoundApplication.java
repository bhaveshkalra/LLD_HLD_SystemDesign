package com.payoneer.MachineCodingRound;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class MachineCodingRoundApplication {

	public static void main(String[] args) {
		SpringApplication.run(MachineCodingRoundApplication.class, args);
	}

}

/**
 * Controller
 *    ↓
 * Service
 *    ↓
 * Repository (in-memory Map)
 *    ↓
 * Models (Template, Notification)
 *
 *
 * Flow:
 * Register Template
 *         ↓
 * Store in templates map
 *
 * Enqueue Notification
 *         ↓
 * Check template exists
 * Check idempotencyKey
 *         ↓
 * Store notification
 * Process Notifications
 *         ↓
 * Find PENDING
 * Print to console
 * Mark SENT
 *
 *
 * Testing =>
 * Client (Postman)
 *        ↓
 * POST /templates
 *        ↓
 * Template stored
 *
 * POST /notifications
 *        ↓
 * Notification created (PENDING)
 *
 * POST /notifications/process
 *        ↓
 * Notification sent (SENT)
 *
 * GET /notifications/{id}
 *        ↓
 * Return status
 *
 */


