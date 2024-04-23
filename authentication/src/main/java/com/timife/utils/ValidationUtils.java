package com.timife.utils;

import com.timife.models.responses.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class ValidationUtils {

    public static ResponseEntity<ErrorResponse> errorEntity(String errorMessage, HttpStatus status) {
        ErrorResponse errorResponse = new ErrorResponse(errorMessage, status);
        return new ResponseEntity<>(errorResponse, status);
    }
    public static ResponseEntity<?> validatePasswordAndEmail(String email, String password) {
        if (password.length() < 6) {
            return errorEntity("Password should not be less than 6 characters", HttpStatus.UNAUTHORIZED);
        } else if (!email.endsWith("@gmail.com") && !email.endsWith("@yahoo.com")) {
            return errorEntity("Enter a valid email address", HttpStatus.UNAUTHORIZED);
        } else {
            return null; // Or any other appropriate response if validation passes
        }
    }
}
