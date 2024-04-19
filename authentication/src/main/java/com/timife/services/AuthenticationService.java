package com.timife.services;

import com.timife.models.requests.AuthRequestDto;
import com.timife.models.requests.UserRequestDto;
import com.timife.models.responses.AuthResponse;
import com.timife.models.responses.RegisterResponse;

public interface AuthenticationService {

    public RegisterResponse register(UserRequestDto requestDto);


    public AuthResponse authenticate(AuthRequestDto authRequestDto);
}
