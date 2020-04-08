package com.kopivad.quizzes.dto;

import lombok.Value;

@Value
public class AuthenticationResponse {
    private String jwt;
}
