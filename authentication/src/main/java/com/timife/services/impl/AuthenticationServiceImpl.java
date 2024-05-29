package com.timife.services.impl;

import com.timife.models.Role;
import com.timife.models.entities.RefreshToken;
import com.timife.models.entities.User;
import com.timife.models.requests.AuthRequestDto;
import com.timife.models.requests.RefreshTokenRequestDto;
import com.timife.models.requests.UserRequestDto;
import com.timife.models.responses.AuthResponse;
import com.timife.models.responses.RegisterResponse;
import com.timife.repositories.RefreshTokenRepository;
import com.timife.repositories.UserRepository;
import com.timife.security.JwtService;
import com.timife.services.AuthenticationService;
import com.timife.services.RefreshTokenService;
import com.timife.utils.CustomException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {
    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final RefreshTokenService refreshTokenService;
    private final AuthenticationManager authManager;
    private final PasswordEncoder passwordEncoder;
    private final RefreshTokenService tokenService;
    private final UserDetailsService userDetailsService;

    @Override
    public RegisterResponse register(User userDto) {
        User user = User.builder()
                .firstName(userDto.getFirstName())
                .lastName(userDto.getLastName())
                .email(userDto.getEmail())
                .password(passwordEncoder.encode(userDto.getPassword()))
                .role(userDto.getRole())
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
        } else {
            throw new UsernameNotFoundException("invalid user request..!!");
        }
    }

    @Override
    public AuthResponse refreshToken(RefreshTokenRequestDto refreshTokenDto) {
        return refreshTokenService.findByToken(refreshTokenDto.getRefreshToken())
                .map(refreshTokenService::verifyExpiration)
                .map(RefreshToken::getUser)
                .map(userInfo -> {
                    String accessToken = jwtService.generateToken(userInfo);
                    return AuthResponse.builder()
                            .accessToken(accessToken)
                            .refreshToken(refreshTokenDto.getRefreshToken()).build();
                }).orElseThrow(() -> new RuntimeException("Refresh Token is not in DB..!!"));
    }

    @Override
    public String validateToken(String token) {
        String userEmail = jwtService.extractUsername(token);
        UserDetails userDetails = userDetailsService.loadUserByUsername(userEmail);
        if (jwtService.isTokenValid(token, userDetails)) {
            return "token is valid, grant access...";
        } else {
            return "token is not valid please";
//            throw new IllegalArgumentException("token not valid");
        }
    }
}
