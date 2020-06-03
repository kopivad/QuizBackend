package com.kopivad.quizzes.form;

import com.kopivad.quizzes.domain.Role;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class UserForm {
    String email;
    String name;
    String password;
    Role role;
}