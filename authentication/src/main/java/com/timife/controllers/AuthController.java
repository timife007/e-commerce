package com.timife.controllers;

import com.timife.models.entities.RefreshToken;
import com.timife.models.entities.User;
import com.timife.models.requests.AuthRequestDto;
import com.timife.models.requests.RefreshTokenRequestDto;
import com.timife.models.requests.UserRequestDto;
import com.timife.repositories.RefreshTokenRepository;
import com.timife.services.AuthenticationService;
import com.timife.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.timife.utils.ValidationUtils.errorEntity;
import static com.timife.utils.ValidationUtils.validatePasswordAndEmail;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UserService detailsService;
    private final RefreshTokenRepository repository;
    private final AuthenticationService authService;





    @PostMapping("/signup")
    public ResponseEntity<?> register(@RequestBody UserRequestDto request) {
        ResponseEntity<?> validation = validatePasswordAndEmail(request.getEmail(), request.getPassword());
        if(validation != null){
            return validation;
        }
        try {
            return ResponseEntity.ok(authService.register(request.toUser()));
        } catch (Exception e) {
            return errorEntity(e.getLocalizedMessage(), HttpStatus.UNAUTHORIZED);
        }
    }

    @PostMapping("vendor/signup")
    public ResponseEntity<?> vendorRegister(@RequestBody UserRequestDto request) {
        ResponseEntity<?> validation = validatePasswordAndEmail(request.getEmail(), request.getPassword());
        if(validation != null){
            return validation;
        }
        try {
            return ResponseEntity.ok(authService.register(request.toVendorUser()));
        } catch (Exception e) {
            return errorEntity(e.getLocalizedMessage(), HttpStatus.UNAUTHORIZED);
        }
    }



    @PostMapping("/login")
    public ResponseEntity<?> authenticateAndGetToken(@RequestBody AuthRequestDto authRequestDTO) {
        ResponseEntity<?> validation = validatePasswordAndEmail(authRequestDTO.getEmail(), authRequestDTO.getPassword());
        if(validation != null){
            return validation;
        }
        try {
            return ResponseEntity.ok(authService.authenticate(authRequestDTO));
        } catch (Exception e) {
            return errorEntity(e.getLocalizedMessage(), HttpStatus.UNAUTHORIZED);
        }
    }


    //Verify that the token exists, if expired, then use it to get the user data to generate a new access token.
    @PostMapping("/refreshToken")
    public ResponseEntity<?> refreshToken(@RequestBody RefreshTokenRequestDto refreshTokenRequestDTO) {
        try {
            return ResponseEntity.ok(authService.refreshToken(refreshTokenRequestDTO));
        } catch (Exception e) {
            return errorEntity(e.getLocalizedMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping
    ResponseEntity<List<RefreshToken>> getAllTokens() {
        return ResponseEntity.ok(repository.findAll());
    }

    @GetMapping("/users")
    ResponseEntity<List<User>> getAllUsers() {
        return ResponseEntity.ok(detailsService.getAllUsers());
    }
}
