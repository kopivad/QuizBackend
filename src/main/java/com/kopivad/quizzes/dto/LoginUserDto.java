package com.kopivad.quizzes.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Value;

@Value
public class LoginUserDto {
    String email;
    String password;

    @JsonCreator
    public LoginUserDto(
            @JsonProperty("name") String email,
            @JsonProperty("password") String password
    ) {
        this.email = email;
        this.password = password;
    }
}
