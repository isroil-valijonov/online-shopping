package com.example.onlineshopping.common.exceptions;

public class TokenException extends RuntimeException {

    public TokenException(String message) {
        super(message);
    }

    public TokenException(String message, int status) {
        super(String.format(message, status));
    }

}
