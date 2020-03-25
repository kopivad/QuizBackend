package com.kopivad.quizzes.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserDto {
    private String name;
    private String email;
    private String password;
    private String role;
}
