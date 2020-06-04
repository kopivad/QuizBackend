package com.kopivad.quizzes.form;

import lombok.Value;

@Value
public class AuthenticationRequestForm {
    String username;
    String password;
}
