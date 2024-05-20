package com.example.onlineshopping.common.exceptions;

import com.example.onlineshopping.common.response.ResponseConstants;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class OtpException extends RuntimeException{
    public OtpException(String massage){
        super(massage);
    }
    public OtpException(String message, Throwable cause){
        super(message, cause);
    }
    public static class OtpEarlyResentException extends OtpException{
        static private final String MESSAGE = "Please try after 0:%d";
        public OtpEarlyResentException(long resentTime){
            super(String.format(MESSAGE, resentTime));
        }

    }

    public static class OtpLimitExitedException extends OtpException{
        static private final String MESSAGE = "Validation limit is reached: %s, Please try after 0:%s";
        public OtpLimitExitedException(int count, LocalDateTime reTryTime){
            super(String.format(MESSAGE, count, DateTimeFormatter.ofPattern(ResponseConstants.RESPONSE_DATE_FORMAT).format(reTryTime)));
        }

    }
    public static class PhoneNumberNotVerified extends OtpException {
        static private String MESSAGE = "Pone Number: %s is not verified";

        public PhoneNumberNotVerified(String phoneNumber) {
            super(String.format(MESSAGE, phoneNumber));
        }
    }

}
