package com.kopivad.quizzes.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Value;

@Value
public class UserDto {
    String email;
    String name;
    String password;

    @JsonCreator
    public UserDto(
            @JsonProperty("email") String email,
            @JsonProperty("name") String name,
            @JsonProperty("password") String password
    ) {
        this.email = email;
        this.name = name;
        this.password = password;
    }
}
