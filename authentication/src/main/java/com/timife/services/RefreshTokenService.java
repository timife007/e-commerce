package com.timife.services;

import com.timife.models.entities.RefreshToken;
import com.timife.repositories.RefreshTokenRepository;
import com.timife.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

@Service
public interface RefreshTokenService {


    RefreshToken createRefreshToken(String username);

    Optional<RefreshToken> findByToken(String token);

    RefreshToken verifyExpiration(RefreshToken token);


}
