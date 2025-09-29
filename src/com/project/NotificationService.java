package com.project;

/**
 * Notification service interface
 */
public interface NotificationService {
    void sendPushNotification(User user, String message);
    void sendWebSocketUpdate(User user, String dataJson);
}
