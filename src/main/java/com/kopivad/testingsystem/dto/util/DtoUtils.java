package com.kopivad.testingsystem.dto.util;

import com.kopivad.testingsystem.domain.Question;
import com.kopivad.testingsystem.domain.Quiz;
import com.kopivad.testingsystem.domain.User;
import com.kopivad.testingsystem.dto.QuestionDto;
import com.kopivad.testingsystem.dto.QuizDto;
import com.kopivad.testingsystem.dto.UserDto;

public class DtoUtils {
    public static User getUserFromDto(UserDto dto) {
        return User.builder()
                .name(dto.getName())
                .password(dto.getPassword())
                .email(dto.getEmail())
                .role(dto.getRole())
                .build();
    }

    public static Quiz getQuizFromDto(QuizDto dto) {
        return Quiz.builder()
                .title(dto.getTitle())
                .active(dto.isActive())
                .description(dto.getDescription())
                .author(User.builder().id(dto.getUserId()).build())
                .build();
    }

    public static Question getQuestionFromDto(QuestionDto questionDto) {
        return Question.builder()
                .title(questionDto.getTitle())
                .quiz(Quiz.builder().id(questionDto.getQuizId()).build())
                .build();
    }
}
