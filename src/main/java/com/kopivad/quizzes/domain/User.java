package com.kopivad.quizzes.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Value;

import java.time.LocalDateTime;
import java.util.List;

@Value
@Builder(toBuilder = true)
@EqualsAndHashCode(of = "id")
@JsonDeserialize(builder = User.UserBuilder.class)
public class User {
    Long id;
    String name;
    String email;
    String password;
    Role role;
    LocalDateTime creationDate;
    List<Quiz> quizzes;
    List<QuizHistory> histories;
    List<QuizSession> sessions;
    Group group;

    @JsonPOJOBuilder(withPrefix = "")
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class UserBuilder { }

}

