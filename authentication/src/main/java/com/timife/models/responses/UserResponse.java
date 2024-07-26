package com.timife.models.responses;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;

@Builder
public record UserResponse(String email, String firstName, String lastName, String address) {
}
