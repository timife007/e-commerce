package com.timife.controllers;

import com.timife.models.entities.DeliveryAddress;
import com.timife.models.entities.RefreshToken;
import com.timife.models.requests.AddressRequest;
import com.timife.models.requests.AuthRequestDto;
import com.timife.models.requests.RefreshTokenRequestDto;
import com.timife.models.requests.UserRequestDto;
import com.timife.models.responses.DeliveryAddressDto;
import com.timife.repositories.RefreshTokenRepository;
import com.timife.services.AuthenticationService;
import com.timife.services.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.ws.rs.Path;
import java.util.List;

import static com.timife.utils.ValidationUtils.errorEntity;
import static com.timife.utils.ValidationUtils.validatePasswordAndEmail;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@Slf4j
public class AuthController {

    private final UserService userService;
    private final RefreshTokenRepository repository;
    private final AuthenticationService authService;

    @PostMapping("/signup")
    public ResponseEntity<?> register(@RequestBody UserRequestDto request) {
        ResponseEntity<?> validation = validatePasswordAndEmail(request.getEmail(), request.getPassword());
        if (validation != null) {
            return validation;
        }
        log.error("SIGNUP code was triggered");

        try {
            log.error("SIGNUP code was triggered");
            return ResponseEntity.ok(authService.register(request.toUser()));
        } catch (Exception e) {
            return errorEntity(e.getLocalizedMessage(), HttpStatus.UNAUTHORIZED);
        }
    }

    @GetMapping("/validate/{token}")
    public String validateToken(@PathVariable("token") String token) {
        log.error("VALIDATE API");
        try{
           return authService.validateToken(token);
        }catch (Exception e){
            return e.getLocalizedMessage();
        }
    }

    @PostMapping("vendor/signup")
    public ResponseEntity<?> vendorRegister(@RequestBody UserRequestDto request) {
        ResponseEntity<?> validation = validatePasswordAndEmail(request.getEmail(), request.getPassword());
        if (validation != null) {
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
        if (validation != null) {
            return validation;
        }
        try {
            log.error("LOGIN code was triggered");
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
    ResponseEntity<?> getAllUsers(Pageable pageable) {
        return ResponseEntity.ok(userService.getAllUsers(pageable));
    }

    @PostMapping("/deliveryAddress")
    ResponseEntity<?> addAddressToUser(@RequestBody AddressRequest addressRequest){
        try {
            return ResponseEntity.ok(userService.addAddressToUser(addressRequest));
        } catch (Exception e) {
            return errorEntity(e.getLocalizedMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/deliveryAddress/{userId}")
    ResponseEntity<List<DeliveryAddressDto>> getUserAddresses(@PathVariable("userId") Integer userId){
            return ResponseEntity.ok(userService.getUserDeliveryAddresses(userId));
    }

    @PutMapping("/deliveryAddress/{id}")
    ResponseEntity<?> updateDeliveryAddress(@PathVariable("id") Long id,@RequestBody AddressRequest addressRequest){
        try {
            return ResponseEntity.ok(userService.updateDeliveryAddress(id, addressRequest));
        } catch (Exception e) {
            return errorEntity(e.getLocalizedMessage(), HttpStatus.BAD_REQUEST);
        }
    }
}
