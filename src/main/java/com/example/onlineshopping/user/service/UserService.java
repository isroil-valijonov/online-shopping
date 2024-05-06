package com.example.onlineshopping.user.service;


import com.example.onlineshopping.common.service.GenericService;
import com.example.onlineshopping.user.dto.UserCreateDto;
import com.example.onlineshopping.user.dto.UserResponseDto;
import com.example.onlineshopping.user.dto.UserUpdateDto;
import com.example.onlineshopping.user.entity.User;
import com.example.onlineshopping.user.mapper.UserDtoMapper;
import com.example.onlineshopping.user.repository.UserRepository;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@Getter
@RequiredArgsConstructor
public class UserService extends GenericService <UUID, User, UserResponseDto, UserCreateDto, UserUpdateDto>{
    private final UserRepository repository;
    private final Class<User> entityClass = User.class;
    private final UserDtoMapper mapper;


    @Override
    protected UserResponseDto internalCreate(UserCreateDto userCreateDto) {
        return null;
    }

    @Override
    protected UserResponseDto internalUpdate(UserUpdateDto userUpdateDto, UUID uuid) {
        return null;
    }

    @Override
    protected UserResponseDto internalUpdate(UserUpdateDto userUpdateDto, User user) {
        return null;
    }
}
