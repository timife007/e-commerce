package com.timife.utils;

import org.springframework.http.HttpStatus;

public class CustomException extends RuntimeException {

//    private String errorCode;
//    private HttpStatus status;

    public CustomException(String message) {
        super(message);
//        this.errorCode = errorCode;
//        this.status = status;
    }
}