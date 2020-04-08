package com.kopivad.quizzes.dto;

import lombok.Value;

@Value
public class UserDto {
    String name;
    String email;
    String password;
    String role;
}
