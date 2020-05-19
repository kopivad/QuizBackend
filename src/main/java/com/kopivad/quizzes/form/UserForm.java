package com.kopivad.quizzes.form;

import com.kopivad.quizzes.domain.Role;
import com.kopivad.quizzes.domain.User;
import lombok.Value;

@Value
public class UserForm {
    String email;
    String name;
    String password;
    Role role;


    public User toUser() {
        return User
                .builder()
                .email(email)
                .name(name)
                .password(password)
                .role(role)
                .build();
    }
}
