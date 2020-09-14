package com.kopivad.quizzes.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Value;

@Value
public class RegisterUserDto {
    String email;
    String name;
    String password;

    @JsonCreator
    public RegisterUserDto(
            @JsonProperty("email") String email,
            @JsonProperty("name") String name,
            @JsonProperty("password") String password
    ) {
        this.email = email;
        this.name = name;
        this.password = password;
    }
}
