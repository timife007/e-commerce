package com.timife.utils;

import lombok.Builder;
import lombok.Data;
import org.springframework.http.HttpStatus;

@Builder
public class CustomException extends RuntimeException {

    private String errorCode;
    private HttpStatus status;

    public CustomException(String message, HttpStatus status) {
        super(message);
        this.status = status;
    }
}
