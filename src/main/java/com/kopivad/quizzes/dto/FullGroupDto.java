package com.kopivad.quizzes.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.kopivad.quizzes.domain.Group;
import com.kopivad.quizzes.domain.Quiz;
import com.kopivad.quizzes.domain.User;
import lombok.Value;

import java.util.List;

@Value
public class FullGroupDto {
    Group group;
    List<User> users;
    List<Quiz> quizzes;

    @JsonCreator
    public FullGroupDto(
            @JsonProperty("group") Group group,
            @JsonProperty("users") List<User> users,
            @JsonProperty("quizzes") List<Quiz> quizzes
    ) {
        this.group = group;
        this.users = users;
        this.quizzes = quizzes;
    }
}
