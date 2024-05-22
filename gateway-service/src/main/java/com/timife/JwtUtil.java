package com.timife;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;

@Component
@Slf4j
public class JwtUtil {

    private static final String SECRET_KEY = "0G6NqCE3pycGnCAY6ixe7VYgy2LMoT9uZ0hlTT/TdFFZDOiOefY5rFHFi3Ma3FL9zzjffYR5wYF6rUfAsUa6I4jYwKUd+NjW1tVUomIxKYBF9ivkDP9qV43roGjDre3uI9aOo+WmBwOlmwlozurBwdqvH0qkb5OOQ4d88RVgKAK0G015zUDekHfdH8//5Rq4GM97BO21UOWiWFEcay5VehvXB8lZ7CorVQawOHouDqW5QCO/nYMMC/cEgtgo8g+JCaXVB48C9kA4vbkIAGakZC5kb9Ys+B2d8trursWiZfYAy4kY85fDqS8x/Qx38ouYfOre1D2WRZkb9uhafbUqPIJHeB7FF4lYZ81HlUq+/Pw=";


    //this way, even if the auth service is down, validation is not affected.
    public void validateToken(final String token){
        log.error("VALIDATETOKEN CHECK" + Jwts.parser().verifyWith(getSignInKey()).build().parseSignedClaims(token).toString());

        Jwts.parser().verifyWith(getSignInKey()).build().parseSignedClaims(token);
    }

    private SecretKey getSignInKey() {
        byte[] bytes = Decoders.BASE64.decode(SECRET_KEY);

        return Keys.hmacShaKeyFor(bytes);
    }
}
