package com.timife.services.impl;

import com.timife.models.Role;
import com.timife.models.entities.User;
import com.timife.models.requests.AuthRequestDto;
import com.timife.models.requests.UserRequestDto;
import com.timife.models.responses.AuthResponse;
import com.timife.models.responses.RegisterResponse;
import com.timife.repositories.RefreshTokenRepository;
import com.timife.repositories.UserRepository;
import com.timife.security.JwtService;
import com.timife.services.AuthenticationService;
import com.timife.services.RefreshTokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {
    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final AuthenticationManager authManager;
    private final PasswordEncoder passwordEncoder;
    private final RefreshTokenService tokenService;

    @Override
    public RegisterResponse register(UserRequestDto requestDto) {
        User user = User.builder()
                .firstName(requestDto.getFirstName())
                .lastName(requestDto.getLastName())
                .email(requestDto.getEmail())
                .password(passwordEncoder.encode(requestDto.getPassword()))
                .role(Role.USER)
                .build();
        if (userRepository.findByEmail(user.getEmail()).isPresent()) {
            throw new UsernameNotFoundException("User already registered");
        }
        userRepository.save(user);
        return RegisterResponse.builder().message("Successfully created").build();
    }

    @Override
    public AuthResponse authenticate(AuthRequestDto authRequestDto) {
        var authMan = authManager.authenticate(new UsernamePasswordAuthenticationToken(authRequestDto.getEmail(), authRequestDto.getPassword()));
        if (authMan.isAuthenticated()) {
            var user = userRepository.findByEmail(authRequestDto.getEmail()).orElseThrow();
            var jwtToken = jwtService.generateToken(user);
            var refreshToken = tokenService.createRefreshToken(user);
            return AuthResponse.builder().accessToken(jwtToken).refreshToken(refreshToken.getToken()).build();
        }else{
            throw new UsernameNotFoundException("invalid user request..!!");
        }
    }
}
