package com.example.onlineshopping.user.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserUpdateDto {

    private String firstName;
    private String lastName;
    private LocalDate birthDate;
    private String phoneNumber;
    private String password;
}
