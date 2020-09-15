package com.kopivad.quizzes.dto;

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

    public FullGroupDto(Group group, List<User> users, List<Quiz> quizzes) {
        this.group = group;
        this.users = users;
        this.quizzes = quizzes;
    }
}
