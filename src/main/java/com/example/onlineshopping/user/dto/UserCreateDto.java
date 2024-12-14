package com.example.onlineshopping.user.dto;

import jakarta.persistence.Column;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserCreateDto {
    @NotBlank(message = "First name cannot be empty")
    private String firstName;

    @NotBlank(message = "Last name cannot be empty")
    private String lastName;

    private LocalDate birthDate;

    @Pattern(regexp = "^998([378]{2}|(9[013-57-9]))\\d{7}$")
    private String phoneNumber;

    @Pattern(regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,20}$", message = "" +
            "1.indicates the string’s beginning " +
            "2.[a-z] makes sure that there is at least one small letter " +
            "3.[A-Z] needs at least one capital letter " +
            "4.[\\\\d] requires at least one digit " +
            "5.[@#$%^&+=] provides a guarantee of at least one special symbol " +
            "6.[8,20]: imposes the minimum length of 8 characters and the maximum length of 20 characters")
    private String password;
}
