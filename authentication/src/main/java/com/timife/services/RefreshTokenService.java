package com.timife.services;

import com.timife.models.entities.RefreshToken;
import com.timife.models.entities.User;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public interface RefreshTokenService {


    RefreshToken createRefreshToken(User user);

    Optional<RefreshToken> findByToken(String token);

    RefreshToken verifyExpiration(RefreshToken token);


}
