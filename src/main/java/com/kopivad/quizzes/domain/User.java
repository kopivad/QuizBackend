package com.kopivad.quizzes.domain;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Value;

import java.time.LocalDateTime;

@Value
public class User {
    Long id;
    String name;
    String email;
    @JsonIgnore
    String password;
    Role role;
    LocalDateTime creationDate;

    @JsonCreator
    public User(
            @JsonProperty("id") Long id,
            @JsonProperty("name") String name,
            @JsonProperty("email") String email,
            @JsonProperty("password") String password,
            @JsonProperty("role") Role role,
            @JsonProperty("creationDate") LocalDateTime creationDate
    ) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
        this.role = role;
        this.creationDate = creationDate;
    }
}

