package com.example.onlineshopping.notification.sms.service;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;

@Service
@ConditionalOnProperty(name = "spring.profiles.active", havingValue = "local")
public class NoSmsNotificationImplService implements SmsNotificationService{

    @Override
    public void sendNotification (String phoneNumber, String message) {
        System.out.printf("%s: %s \n",phoneNumber,message);
    }
}
