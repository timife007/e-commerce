package com.timife.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import javax.crypto.SecretKey;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
public class JwtService {

    private static final String SECRET_KEY = "0G6NqCE3pycGnCAY6ixe7VYgy2LMoT9uZ0hlTT/TdFFZDOiOefY5rFHFi3Ma3FL9zzjffYR5wYF6rUfAsUa6I4jYwKUd+NjW1tVUomIxKYBF9ivkDP9qV43roGjDre3uI9aOo+WmBwOlmwlozurBwdqvH0qkb5OOQ4d88RVgKAK0G015zUDekHfdH8//5Rq4GM97BO21UOWiWFEcay5VehvXB8lZ7CorVQawOHouDqW5QCO/nYMMC/cEgtgo8g+JCaXVB48C9kA4vbkIAGakZC5kb9Ys+B2d8trursWiZfYAy4kY85fDqS8x/Qx38ouYfOre1D2WRZkb9uhafbUqPIJHeB7FF4lYZ81HlUq+/Pw=";

    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    //Extract a claim for a particular user from the token, claims can include expiration time
    // and so on, information about a current user, including username, password and so on.
    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    //generate token for authentication.
    private String generateToken(Map<String, Object> extraClaims, UserDetails userDetails) {
        return Jwts.builder()
                .claims()
                .subject(userDetails.getUsername())
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + 1000 * 60 * 24))
                .add(extraClaims)
                .and()
                .signWith(getSignInKey())
                .compact();
    }

    public String generateToken(UserDetails userDetails) {
        return generateToken(new HashMap<>(), userDetails);
    }

    //Just in case I don't want the userdetails to be used, then I can move this to the gateway.
    public boolean isTokenValid(String token, UserDetails userDetails) { //checks if the token belongs to the userDetails and if the token is still valid, not expired.
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername())) && !isTokenExpired(token);
//        try{
//            Jwts.parser().verifyWith(getSignInKey()).build().parseSignedClaims(token);
//            if(!isTokenExpired(token)){
//                return true;
//            };
//            return false;
//        }catch (Exception e){
//            throw new IllegalArgumentException("");
//        }
    }

    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    //returns the expiration time of the token
    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }


    //extract
    private Claims extractAllClaims(String token) {
        return Jwts.parser().verifyWith(getSignInKey()).build().parseSignedClaims(token).getPayload();
    }

    private SecretKey getSignInKey() {
        byte[] bytes = Decoders.BASE64.decode(SECRET_KEY);

        return Keys.hmacShaKeyFor(bytes);
    }
}
