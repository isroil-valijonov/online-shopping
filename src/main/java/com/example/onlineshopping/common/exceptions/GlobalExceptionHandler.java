package com.example.onlineshopping.common.exceptions;

import com.example.onlineshopping.common.response.CommonResponse;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(DataIntegrityViolationException.class)

    public CommonResponse handleDataIntegrityViolationException(DataIntegrityViolationException ex) {
        return new CommonResponse(ex.getMessage(), LocalDateTime.now(), HttpStatus.BAD_REQUEST.value());
    }

}
