package com.example.onlineshopping.notification.sms.service;

import com.example.onlineshopping.notification.sms.dto.SendSmsRequestDto;
import com.example.onlineshopping.notification.sms.eskiz.EskizFeignClient;
import feign.FeignException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;

@Service
@ConditionalOnProperty(name = "spring.profiles.active", havingValue = "production")
@RequiredArgsConstructor
@Slf4j
public class SmsNotificationImplService implements SmsNotificationService {
    @Value("${online-shopping.notification.sms.eskiz}")
    private String token;
    private final EskizFeignClient eskizFeignClient;

    @Override
    public void sendNotification(String phoneNumber, String message) {
        try{
            eskizFeignClient.smsSend("Bearer "+token,
                    new SendSmsRequestDto(phoneNumber,message));
        }catch(FeignException.Unauthorized e){
            token = eskizFeignClient.refresh("Bearer " + token).getData().get("token");
            eskizFeignClient.smsSend("Bearer "+token,new SendSmsRequestDto(phoneNumber,message));
        }catch (Exception e){
            log.error("Exception happened while sending notification.phoneNumber: {} message:{}",phoneNumber,message,e);
        }
    }
}
