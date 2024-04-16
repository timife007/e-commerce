package com.timife.services;

import com.timife.models.requests.AuthRequestDto;
import com.timife.models.requests.UserRequestDto;
import com.timife.models.responses.AuthResponse;

public interface AuthenticationService {

    public AuthResponse register(UserRequestDto requestDto);


    public AuthResponse authenticate(AuthRequestDto authRequestDto);
}
