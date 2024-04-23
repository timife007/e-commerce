package com.timife.services;

import com.timife.models.entities.RefreshToken;
import com.timife.models.entities.User;
import com.timife.models.requests.AuthRequestDto;
import com.timife.models.requests.RefreshTokenRequestDto;
import com.timife.models.requests.UserRequestDto;
import com.timife.models.responses.AuthResponse;
import com.timife.models.responses.RegisterResponse;

public interface AuthenticationService {

    public RegisterResponse register(User requestDto);


    public AuthResponse authenticate(AuthRequestDto authRequestDto);


    public AuthResponse refreshToken(RefreshTokenRequestDto refreshTokenDto);
}
