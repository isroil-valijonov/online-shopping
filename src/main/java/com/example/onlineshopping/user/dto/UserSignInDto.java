package com.example.onlineshopping.user.dto;

import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserSignInDto {

    @Pattern(regexp = "^998([378]{2}|(9[013-57-9]))\\d{7}$")
    private String phoneNumber;

    @Pattern(regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,20}$", message = "^: indicates the string’s beginning\n" +
            "(?=.*[a-z]): makes sure that there is at least one small letter\n" +
            "(?=.*[A-Z]): needs at least one capital letter\n" +
            "(?=.*\\\\d): requires at least one digit\n" +
            "(?=.*[@#$%^&+=]): provides a guarantee of at least one special symbol\n" +
            ".{8,20}: imposes the minimum length of 8 characters and the maximum length of 20 characters")
    private String password;
}
