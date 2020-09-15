package com.kopivad.quizzes.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.kopivad.quizzes.domain.Role;
import lombok.Value;

@Value
public class SaveUserDto {
    String email;
    String name;
    String password;
    Role role;

    @JsonCreator
    public SaveUserDto(
            @JsonProperty("email") String email,
            @JsonProperty("name") String name,
            @JsonProperty("password") String password,
            @JsonProperty("role") Role role
    ) {
        this.email = email;
        this.name = name;
        this.password = password;
        this.role = role;
    }
}
