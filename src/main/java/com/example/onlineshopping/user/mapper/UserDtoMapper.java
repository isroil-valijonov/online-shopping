package com.example.onlineshopping.user.mapper;

import com.example.onlineshopping.common.mapper.GenericMapper;
import com.example.onlineshopping.user.dto.UserCreateDto;
import com.example.onlineshopping.user.dto.UserResponseDto;
import com.example.onlineshopping.user.dto.UserUpdateDto;
import com.example.onlineshopping.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;


@Component
@RequiredArgsConstructor
public class UserDtoMapper extends GenericMapper<User, UserCreateDto, UserResponseDto, UserUpdateDto>{
    private final ModelMapper modelMapper;

    @Override
    public User toEntity(UserCreateDto userCreateDto) {
        return modelMapper.map(userCreateDto, User.class);
    }

    @Override
    public UserResponseDto toResponse(User user) {
        return modelMapper.map(user, UserResponseDto.class);
    }

    @Override
    public void toUpdate(UserUpdateDto userUpdateDto, User user) {
        modelMapper.map(userUpdateDto, user);
    }
}
