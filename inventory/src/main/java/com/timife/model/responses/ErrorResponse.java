package com.timife.model.responses;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class ErrorResponse {
    String errorMessage;
    HttpStatus status;
}