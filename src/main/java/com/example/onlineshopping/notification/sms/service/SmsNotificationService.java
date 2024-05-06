package com.example.onlineshopping.notification.sms.service;

public interface SmsNotificationService {
    void sendNotification (String phoneNumber, String message);
}
