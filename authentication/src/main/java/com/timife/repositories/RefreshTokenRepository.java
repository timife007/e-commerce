package com.timife.repositories;

import com.timife.models.entities.RefreshToken;
import com.timife.models.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Integer> {


    @Query(
            value = "SELECT * FROM refresh_token u WHERE u.token = ?1",
            nativeQuery = true)
    Optional<RefreshToken> findByToken(String token);


    RefreshToken findByUser(User user);
}
