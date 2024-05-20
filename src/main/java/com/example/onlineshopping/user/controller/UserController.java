package com.example.onlineshopping.user.controller;

import com.example.onlineshopping.common.response.CommonResponse;
import com.example.onlineshopping.otp.service.OtpService;
import com.example.onlineshopping.user.dto.*;
import com.example.onlineshopping.user.entity.User;
import com.example.onlineshopping.user.service.UserService;
import com.example.onlineshopping.security.JwtService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    private final OtpService otpService;
    private final JwtService jwtService;

    @PostMapping("/api/v1/auth/validate") // /user/api
    public ResponseEntity<CommonResponse> validateOtp
            (@RequestBody @Valid ValidatePhoneNumberRequestDto requestDto){
        CommonResponse commonResponse = otpService.sendSms(requestDto);
        return ResponseEntity.ok(commonResponse);
    }
    @PostMapping("/api/v1/auth/sign-up")
    public ResponseEntity<UserResponseDto> signUp(@RequestBody @Valid UserCreateDto userCreateDto){
        UserResponseDto userResponseDto = userService.create(userCreateDto);
        String token = jwtService.generateToken(userResponseDto.getPhoneNumber());
        userResponseDto.setToken(token);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .header(HttpHeaders.AUTHORIZATION,"Bearer "+token)
                .body(userResponseDto);
    }
    @PostMapping("/api/v1/auth/sign-in")
    public ResponseEntity<UserResponseDto> signIn(@RequestBody @Valid UserSignInDto userSignInDto){
        UserResponseDto userResponseDto = userService.signIn(userSignInDto);
        String token = jwtService.generateToken(userResponseDto.getPhoneNumber());
        userResponseDto.setToken(token);
        return ResponseEntity
                .status(HttpStatus.OK)
                .header(HttpHeaders.AUTHORIZATION,"Bearer "+token)
                .body(userResponseDto);
    }


    @GetMapping("/user/{id}")
    public ResponseEntity<UserResponseDto> getUserById(@PathVariable UUID id){
        UserResponseDto user = userService.getById(id);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(user);
    }

    @GetMapping("/get-all-user")
    public ResponseEntity<AllUserResponseDto> getAllUsers(){
        AllUserResponseDto allUser = userService.getAllUser();
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(allUser);
    }


    @PutMapping("/update/{id}")
    public ResponseEntity<UserResponseDto> update(@PathVariable UUID id, @RequestBody UserUpdateDto userUpdateDto){
        UserResponseDto update = userService.update(id, userUpdateDto);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(update);
    }




}
