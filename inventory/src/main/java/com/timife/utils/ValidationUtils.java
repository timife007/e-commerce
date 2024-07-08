package com.timife.utils;

import com.timife.model.responses.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class ValidationUtils {

    public static ResponseEntity<ErrorResponse> errorEntity(String errorMessage, HttpStatus status) {
        ErrorResponse errorResponse = new ErrorResponse(errorMessage, status);
        return new ResponseEntity<>(errorResponse, status);
    }

}
