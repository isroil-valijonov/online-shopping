package com.example.onlineshopping.user.service;


import com.example.onlineshopping.common.exceptions.OtpException;
import com.example.onlineshopping.common.service.GenericService;
import com.example.onlineshopping.otp.entity.OTP;
import com.example.onlineshopping.otp.repository.OtpRepository;
import com.example.onlineshopping.user.dto.*;
import com.example.onlineshopping.user.entity.User;
import com.example.onlineshopping.user.mapper.UserDtoMapper;
import com.example.onlineshopping.user.repository.UserRepository;
import com.example.onlineshopping.user.role.Role;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@Getter
@RequiredArgsConstructor
public class UserService extends GenericService <UUID, User, UserResponseDto, UserCreateDto, UserUpdateDto>{
    private final UserRepository repository;
    private final Class<User> entityClass = User.class;
    private final UserDtoMapper mapper;
    private final PasswordEncoder passwordEncoder;
    private final OtpRepository otpRepository;


    @Override
    protected UserResponseDto internalUpdate(UserUpdateDto userUpdateDto, UUID uuid) {
        return null;
    }

    @Override
    protected UserResponseDto internalUpdate(UserUpdateDto userUpdateDto, User user) {
        return null;
    }
    @Override
    protected UserResponseDto internalCreate(UserCreateDto userCreateDto) {
        User entity = mapper.toEntity(userCreateDto);
        entity.setPassword(passwordEncoder.encode(entity.getPassword()));
        entity.setRole(Role.USER);
        isPhoneNumberVerified(userCreateDto.getPhoneNumber());
        User saved = repository.save(entity);
        return mapper.toResponse(saved);
    }


    private void isPhoneNumberVerified(String phoneNumber) {
        OTP otp = otpRepository
                .findById(phoneNumber)
                .orElseThrow(() -> new OtpException.PhoneNumberNotVerified(phoneNumber));

        if (!otp.isVerified()) {
            throw new OtpException.PhoneNumberNotVerified(phoneNumber);
        }
    }

    @Transactional
    public UserResponseDto signIn(@Valid UserSignInDto signInDto) {
        User user = repository.findByPhoneNumber(signInDto.getPhoneNumber())
                .orElseThrow(() -> new BadCredentialsException("Username or password is not correct"));

        if (!passwordEncoder.matches(signInDto.getPassword(), user.getPassword())) {
            throw new BadCredentialsException("Username or password is not correct");
        }

        return mapper.toResponse(user);
    }

    public UserResponseDto update(UUID id, UserUpdateDto userUpdateDto){
        User user = findByUserId(id);
        if (user == null){
            throw new RuntimeException("User not found with id: " + id);
        }
        user.setPhoneNumber(userUpdateDto.getPhoneNumber());
        user.setFirstName(userUpdateDto.getFirstName());
        user.setLastName(userUpdateDto.getLastName());
        user.setBirthDate(userUpdateDto.getBirthDate());
        user.setUpdate(LocalDateTime.now());
        user.setPassword(passwordEncoder.encode(userUpdateDto.getPassword()));
        repository.save(user);
        return mapper.toResponse(user);
    }


    public UserResponseDto getById(UUID id){
        User user = findByUserId(id);
        return mapper.toResponse(user);
    }

    public AllUserResponseDto getAllUser(){
        List<User> all = repository.findAll();
        return new AllUserResponseDto(all);
    }

    public User findByUserId(UUID id){
        return repository.findById(id).orElseThrow(() -> new RuntimeException("User not found"));
    }
}
