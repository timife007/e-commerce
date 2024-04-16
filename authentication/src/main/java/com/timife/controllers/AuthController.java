package com.timife.controllers;

import com.timife.models.Role;
import com.timife.models.entities.RefreshToken;
import com.timife.models.entities.User;
import com.timife.models.requests.AuthRequestDto;
import com.timife.models.requests.RefreshTokenRequestDto;
import com.timife.models.requests.UserRequestDto;
import com.timife.models.responses.AuthResponse;
import com.timife.repositories.UserRepository;
import com.timife.security.JwtService;
import com.timife.services.AuthenticationService;
import com.timife.services.RefreshTokenService;
import com.timife.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    private final RefreshTokenService refreshTokenService;
    private final UserService detailsService;
    private final JwtService jwtService;
    private final AuthenticationService authService;

    @PostMapping("/signup")
    public ResponseEntity<AuthResponse> register(@RequestBody UserRequestDto request) {
        return ResponseEntity.ok(authService.register(request));
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> AuthenticateAndGetToken(@RequestBody AuthRequestDto authRequestDTO) {
        return ResponseEntity.ok(authService.authenticate(authRequestDTO));
    }


    //Verify that the token exists, if expired, then use it to get the user data to generate a new access token.
    @PostMapping("/refreshToken")
    public AuthResponse refreshToken(@RequestBody RefreshTokenRequestDto refreshTokenRequestDTO) {
        return refreshTokenService.findByToken(refreshTokenRequestDTO.getRefreshToken())
                .map(refreshTokenService::verifyExpiration)
                .map(RefreshToken::getUser)
                .map(userInfo -> {
                    var userDetails = detailsService.getUser(userInfo.getEmail());
                    String accessToken = jwtService.generateToken(userDetails);
                    return AuthResponse.builder()
                            .accessToken(accessToken)
                            .refreshToken(refreshTokenRequestDTO.getRefreshToken()).build();
                }).orElseThrow(() -> new RuntimeException("Refresh Token is not in DB..!!"));
    }
}
