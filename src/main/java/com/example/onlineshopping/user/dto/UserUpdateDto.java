package com.example.onlineshopping.user.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserUpdateDto {

    private String firstName;
    private String lastName;
    private LocalDateTime birthDate;
    private String phoneNumber;
}
