package com.kopivad.quizzes.service;

import com.kopivad.quizzes.domain.User;
import io.jsonwebtoken.Claims;

public interface JwtService {
    String extractSubject(String authToken);

    Claims getClaimsFromToken(String authToken);

    boolean validateToken(String authToken);

    String generateToken(User user);
}
