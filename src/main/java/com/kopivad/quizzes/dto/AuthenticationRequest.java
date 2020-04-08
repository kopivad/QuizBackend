package com.kopivad.quizzes.dto;

import lombok.Value;

@Value
public class AuthenticationRequest {
    String username;
    String password;
}
