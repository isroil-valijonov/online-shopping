package com.example.onlineshopping.user.controller;

import com.example.onlineshopping.common.response.CommonResponse;
import com.example.onlineshopping.user.dto.*;
import com.example.onlineshopping.user.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

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

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<CommonResponse> userDelete(@PathVariable UUID id){
        CommonResponse commonResponse = userService.userDelete(id);
        return ResponseEntity
                .status(commonResponse.getStatus())
                .body(commonResponse);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<UserResponseDto> update(@PathVariable UUID id, @RequestBody @Valid UserUpdateDto userUpdateDto){

        return userService.update(id, userUpdateDto);
    }


}
