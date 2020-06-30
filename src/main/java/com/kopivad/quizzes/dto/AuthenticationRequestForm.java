package com.kopivad.quizzes.dto;

import lombok.Value;

@Value
public class AuthenticationRequestForm {
    String username;
    String password;
}
