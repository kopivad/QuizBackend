package com.kopivad.quizzes.utils;

import com.kopivad.quizzes.domain.Answer;
import com.kopivad.quizzes.domain.Question;
import com.kopivad.quizzes.domain.Quiz;
import com.kopivad.quizzes.domain.User;
import com.kopivad.quizzes.dto.AnswerDto;
import com.kopivad.quizzes.dto.QuestionDto;
import com.kopivad.quizzes.dto.QuizDto;
import com.kopivad.quizzes.dto.UserDto;

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
                .type(questionDto.getType())
                .build();
    }

    public static Answer getAnswerFromDto(AnswerDto answerDto) {
        return Answer.builder()
                .text(answerDto.getText())
                .isRight(answerDto.isRight())
                .type(answerDto.getType())
                .question(Question.builder().id(answerDto.getQuestionId()).build())
                .build();
    }
}
