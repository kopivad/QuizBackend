package com.kopivad.quizzes.service;

import io.jsonwebtoken.Claims;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Date;
import java.util.function.Function;

public interface JwtService {
    String generateToken(UserDetails userDetails);

    Boolean validateToken(String token, UserDetails userDetails);

    <T> T extractClaim(String token, Function<Claims, T> claimsResolver);

    Date extractExpiration(String token);

    String extractUsername(String token);
}
