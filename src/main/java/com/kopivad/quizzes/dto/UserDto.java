package com.kopivad.quizzes.dto;

import com.kopivad.quizzes.domain.Role;
import lombok.Builder;
import lombok.Value;

@Value
@Builder(toBuilder = true)
public class UserDto {
    String name;
    String email;
    String password;
    Role role;
}
