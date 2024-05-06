package com.example.onlineshopping.notification.sms.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SendSmsRequestDto {
    @JsonProperty("mobile_phone")
    private String phoneNumber;
    private String message;
    private final String from = "4546";
}
