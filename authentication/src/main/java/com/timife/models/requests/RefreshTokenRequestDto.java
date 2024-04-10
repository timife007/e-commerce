package com.timife.models.requests;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;

@AllArgsConstructor
@Data
@Builder
@NoArgsConstructor
public class RefreshTokenRequestDto {

    private String refreshToken;
}
