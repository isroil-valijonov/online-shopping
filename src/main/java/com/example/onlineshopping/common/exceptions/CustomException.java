package com.example.onlineshopping.common.exceptions;



public class CustomException extends RuntimeException{

    public CustomException(String massage){
        super(massage);
    }
    public CustomException(String massage, int status){
        super(String.format(massage, status));
    }


}
