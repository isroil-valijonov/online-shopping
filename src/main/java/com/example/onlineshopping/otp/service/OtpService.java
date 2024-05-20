package com.example.onlineshopping.otp.service;

import com.example.onlineshopping.common.exceptions.OtpException;
import com.example.onlineshopping.common.response.CommonResponse;
import com.example.onlineshopping.notification.sms.service.SmsNotificationService;
import com.example.onlineshopping.otp.entity.OTP;
import com.example.onlineshopping.otp.repository.OtpRepository;
import com.example.onlineshopping.security.JwtService;
import com.example.onlineshopping.user.dto.ValidatePhoneNumberRequestDto;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class OtpService {
    private final OtpRepository otpRepository;
    private final Random random = new Random();
    @Value("${online-shopping.otp.retry-wait-time}")
    private int retryWaitTime;
    @Value("${online-shopping.otp.retry-count}")
    private int retryCount;
    @Value("${online-shopping.otp.time-to-live}")
    private int timeToLive;
    private final String VERIFICATION_MESSAGE = "Your verification code is: %d%n";
    private final SmsNotificationService smsNotificationService;
    private final JwtService jwtService;

    @Transactional
    public CommonResponse sendSms(ValidatePhoneNumberRequestDto requestDto) {
        String phoneNumber = requestDto.getPhoneNumber();
        Optional<OTP> existingOtp = otpRepository.findById(phoneNumber);

        if (requestDto.getOtp() == null) {
            if (existingOtp.isPresent()) {
                return reTry(existingOtp.get());
            }

            OTP otp = sendSmsInternal(phoneNumber);
            otpRepository.save(otp);

            return new CommonResponse("Sms was sent successfully", LocalDateTime.now(), HttpStatus.OK.value());
        }

        OTP otp = existingOtp
                .orElseThrow(() -> new EntityNotFoundException("we didn't send you any verification code"));

        if (otp.getCode() == requestDto.getOtp()) {
            otp.setVerified(true);
            otpRepository.save(otp);
            return new CommonResponse("Otp was successfully verified", LocalDateTime.now(), HttpStatus.OK.value());
        } else {
            return new CommonResponse("Otp was incorrect", LocalDateTime.now(), HttpStatus.BAD_REQUEST.value());
        }
    }

    public CommonResponse reTry(OTP otp) {
        if (otp.getLastSentTime().plusSeconds(retryWaitTime).isAfter(LocalDateTime.now())) {
            long resentTime = Duration.between(otp.getLastSentTime(), LocalDateTime.now()).getSeconds();
            throw new OtpException.OtpEarlyResentException(retryWaitTime - resentTime);
        }

        if (otp.getSentCount() >= retryCount) {
            throw new OtpException.OtpLimitExitedException(otp.getSentCount(), otp.getCreated().plusSeconds(timeToLive));
        }

        OTP resentOtp = sendSmsInternal(otp);
        otpRepository.save(resentOtp);

        return new CommonResponse("Sms was re-sent successfully", LocalDateTime.now(), HttpStatus.OK.value());
    }

    private OTP sendSmsInternal(String phoneNumber) {
        int code = random.nextInt(100000, 999999);
        smsNotificationService.sendNotification(phoneNumber, VERIFICATION_MESSAGE.formatted(code));
        return new OTP(phoneNumber, code, LocalDateTime.now(), 1, LocalDateTime.now(),false);
    }

    private OTP sendSmsInternal(OTP otp) {
        int code = random.nextInt(100000, 999999);
        smsNotificationService.sendNotification(otp.getPhoneNumber(), VERIFICATION_MESSAGE.formatted(code));
        otp.setCode(code);
        otp.setLastSentTime(LocalDateTime.now());
        otp.setSentCount(otp.getSentCount() + 1);
        return otp;
    }
}
