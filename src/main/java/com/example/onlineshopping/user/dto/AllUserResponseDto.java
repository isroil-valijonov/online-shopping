package com.example.onlineshopping.user.dto;

import com.example.onlineshopping.user.entity.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AllUserResponseDto {
    List<User> users;
}
