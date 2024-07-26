package com.timife.model.responses;

import lombok.*;

@Builder
public record UserResponse(String email, String firstName, String lastName, String address) {
}