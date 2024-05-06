package com.example.onlineshopping.user.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserCreateDto {
    private String firstName;
    private String lastName;
    private LocalDate birthDate;
    private String phoneNumber;
    private String password;
}
