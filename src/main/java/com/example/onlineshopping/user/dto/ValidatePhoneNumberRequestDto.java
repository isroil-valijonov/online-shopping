package com.example.onlineshopping.user.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ValidatePhoneNumberRequestDto {
    @NotBlank
    @Pattern(regexp = "^998([378]{2}|(9[013-57-9]))\\d{7}$")
    private String phoneNumber;
    private Integer otp;
}
