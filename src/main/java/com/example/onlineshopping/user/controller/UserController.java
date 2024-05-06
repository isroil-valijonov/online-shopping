package com.example.onlineshopping.user.controller;

import com.example.onlineshopping.common.response.CommonResponse;
import com.example.onlineshopping.otp.service.OtpService;
import com.example.onlineshopping.user.dto.ValidatePhoneNumberRequestDto;
import com.example.onlineshopping.user.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    private final OtpService otpService;

    @PostMapping("/api/v1/auth/validate")
    public ResponseEntity<CommonResponse> validateOtp
            (@RequestBody @Valid ValidatePhoneNumberRequestDto requestDto){
        CommonResponse commonResponse = otpService.sendSms(requestDto);
        return ResponseEntity.ok(commonResponse);
    }
//    @PostMapping("/api/v1/auth/sign-up")
//    public ResponseEntity<UserResponseDto> signUp(@RequestBody @Valid UserCreatedDto userCreateDto){
//        UserResponseDto userResponseDto = userService.create(userCreateDto);
//        String token = jwtService.generateToken(userResponseDto.getPhoneNumber());
//        return ResponseEntity
//                .status(HttpStatus.CREATED)
//                .header(HttpHeaders.AUTHORIZATION,"Bearer "+token)
//                .body(userResponseDto);
//    }
//    @PostMapping("/api/v1/auth/sign-in")
//    public ResponseEntity<UserResponseDto> signIn(@RequestBody @Valid UserSignInDto userSignInDto){
//        UserResponseDto userResponseDto = userService.signIn(userSignInDto);
//        String token = jwtService.generateToken(userResponseDto.getPhoneNumber());
//        return ResponseEntity
//                .status(HttpStatus.OK)
//                .header(HttpHeaders.AUTHORIZATION,"Bearer "+token)
//                .body(userResponseDto);
//    }




}
