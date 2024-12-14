package com.example.onlineshopping.user.service;


import com.example.onlineshopping.book.entity.Book;
import com.example.onlineshopping.book.repository.BookRepository;
import com.example.onlineshopping.common.exceptions.CustomException;
import com.example.onlineshopping.common.exceptions.OtpException;
import com.example.onlineshopping.common.response.CommonResponse;
import com.example.onlineshopping.common.service.GenericService;
import com.example.onlineshopping.otp.entity.OTP;
import com.example.onlineshopping.otp.repository.OtpRepository;
import com.example.onlineshopping.security.JwtService;
import com.example.onlineshopping.user.dto.*;
import com.example.onlineshopping.user.entity.User;
import com.example.onlineshopping.user.mapper.UserDtoMapper;
import com.example.onlineshopping.user.repository.UserRepository;
import com.example.onlineshopping.user.role.Role;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

@Service
@Getter
@RequiredArgsConstructor
public class UserService extends GenericService<UUID, User, UserResponseDto, UserCreateDto, UserUpdateDto> {
    private final UserRepository repository;
    private final Class<User> entityClass = User.class;
    private final UserDtoMapper mapper;
    private final PasswordEncoder passwordEncoder;
    private final OtpRepository otpRepository;
    private final JwtService jwtService;
    private final BookRepository bookRepository;

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

    public ResponseEntity<UserResponseDto> update(UUID id, UserUpdateDto userUpdateDto) {
        if (!repository.existsById(id)) {
            throw new CustomException("User not found with id: " + id, HttpStatus.NOT_FOUND.value());
        }

        User user = repository.findByUserId(id);
        if (Objects.equals(user.getPhoneNumber(), userUpdateDto.getPhoneNumber())) {
            user.setFirstName(userUpdateDto.getFirstName());
            user.setLastName(userUpdateDto.getLastName());
            user.setBirthDate(userUpdateDto.getBirthDate());
            user.setUpdate(LocalDateTime.now());
            user.setPassword(passwordEncoder.encode(userUpdateDto.getPassword()));
            repository.save(user);
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(mapper.toResponse(user));
        }
        Optional<User> byPhoneNumber = repository.findByPhoneNumber(userUpdateDto.getPhoneNumber());
        if (byPhoneNumber.isPresent()) {
            throw new DuplicateKeyException("Phone number is already registered");
        }
        UserResponseDto userResponseDto = new UserResponseDto();
        String token = jwtService.generateToken(userUpdateDto.getPhoneNumber());
        user.setPhoneNumber(userUpdateDto.getPhoneNumber());
        user.setFirstName(userUpdateDto.getFirstName());
        user.setLastName(userUpdateDto.getLastName());
        user.setBirthDate(userUpdateDto.getBirthDate());
        user.setUpdate(LocalDateTime.now());
        user.setPassword(passwordEncoder.encode(userUpdateDto.getPassword()));
        repository.save(user);
        System.out.println(token);
        userResponseDto = mapper.toResponse(user);
        userResponseDto.setToken(token);
        System.out.println(userResponseDto);
        return ResponseEntity
                .status(HttpStatus.OK)
                .header(HttpHeaders.AUTHORIZATION, token)
                .body(userResponseDto);
    }

    @Transactional
    public CommonResponse userDelete(UUID id){
        try {
            if (!repository.existsById(id)){
                return new CommonResponse("User not found with: " + id, LocalDateTime.now(), HttpStatus.NOT_FOUND.value());
            }
            List<Book> bookListByAuthorId = bookRepository.getBookListByAuthorId(id);
            bookListByAuthorId
                    .forEach(book -> book.setAuthor(null));

            bookRepository.saveAll(bookListByAuthorId);
            repository.deleteById(id);
            return new CommonResponse("User has been deleted", LocalDateTime.now(), HttpStatus.OK.value());
        } catch (Exception e) {
            throw new CustomException(e.getMessage(), HttpStatus.BAD_REQUEST.value());
        }
    }

    public UserResponseDto getById(UUID id) {
        User user = findByUserId(id);
        return mapper.toResponse(user);
    }

    public AllUserResponseDto getAllUser() {
        List<User> all = repository.findAll();
        return new AllUserResponseDto(all);
    }

    public User findByUserId(UUID id) {
        return repository.findById(id).orElseThrow(() -> new CustomException("User not found:" + id, HttpStatus.NOT_FOUND.value()));
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
