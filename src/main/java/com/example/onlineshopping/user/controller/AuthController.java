package com.example.onlineshopping.user.controller;

import com.example.onlineshopping.common.response.CommonResponse;
import com.example.onlineshopping.otp.service.OtpService;
import com.example.onlineshopping.security.JwtService;
import com.example.onlineshopping.user.dto.UserCreateDto;
import com.example.onlineshopping.user.dto.UserResponseDto;
import com.example.onlineshopping.user.dto.UserSignInDto;
import com.example.onlineshopping.user.dto.ValidatePhoneNumberRequestDto;
import com.example.onlineshopping.user.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    private final UserService userService;
    private final OtpService otpService;
    private final JwtService jwtService;

    @PostMapping("/api/v1/validate")
    public ResponseEntity<CommonResponse> validateOtp
            (@RequestBody @Valid ValidatePhoneNumberRequestDto requestDto) {
        CommonResponse commonResponse = otpService.sendSms(requestDto);
        return ResponseEntity.ok(commonResponse);
    }

    @PostMapping("/api/v1/sign-up")
    public ResponseEntity<UserResponseDto> signUp(@RequestBody @Valid UserCreateDto userCreateDto) {
        UserResponseDto userResponseDto = userService.create(userCreateDto);
        String token = jwtService.generateToken(userResponseDto.getPhoneNumber());
        userResponseDto.setToken(token);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                .body(userResponseDto);
    }

    @PostMapping("/api/v1/sign-in")
    public ResponseEntity<UserResponseDto> signIn(@RequestBody @Valid UserSignInDto userSignInDto) {
        UserResponseDto userResponseDto = userService.signIn(userSignInDto);
        String token = jwtService.generateToken(userResponseDto.getPhoneNumber());
        userResponseDto.setToken(token);
        return ResponseEntity
                .status(HttpStatus.OK)
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                .body(userResponseDto);
    }
}
