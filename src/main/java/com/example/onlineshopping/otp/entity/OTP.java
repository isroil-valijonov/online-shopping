package com.example.onlineshopping.otp.entity;

import jakarta.persistence.EntityListeners;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.data.redis.core.RedisHash;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@RedisHash(value = "otp", timeToLive = 3600)
@EntityListeners(AuditingEntityListener.class)
public class OTP {
    @Id
    private String phoneNumber;
    private int code;

    @LastModifiedDate
    private LocalDateTime lastSentTime;
    private int sentCount;

    @CreatedDate
    private LocalDateTime created;
    private boolean isVerified;
}
