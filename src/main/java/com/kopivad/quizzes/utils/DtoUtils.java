package com.kopivad.quizzes.utils;

import com.kopivad.quizzes.domain.*;
import com.kopivad.quizzes.dto.*;

import java.util.function.Function;
import java.util.stream.Collectors;

public class DtoUtils {
    public static User getUserFromDto(UserDto dto) {
        return User.builder()
                .name(dto.getName())
                .password(dto.getPassword())
                .email(dto.getEmail())
                .role(Role.valueOf(dto.getRole()))
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
                .question(Question.builder().id(answerDto.getQuestionId()).build())
                .build();
    }

    public static Quiz getQuizFromFullDto(FullQuizDto dto) {
        return Quiz.builder()
                .title(dto.getTitle())
                .active(dto.isActive())
                .description(dto.getDescription())
                .author(User.builder().id(dto.getAuthor_id()).build())
                .questions(
                        dto.getQuestions()
                                .stream()
                                .map(getFullQuestionDtoQuestionFunction())
                                .collect(Collectors.toList())
                )
                .build();
    }

    private static Function<FullQuestionDto, Question> getFullQuestionDtoQuestionFunction() {
        return q -> Question
                .builder()
                .type(q.getType())
                .title(q.getTitle())
                .answers(
                        q.getAnswers()
                                .stream()
                                .map(getFullAnswerDtoAnswerFunction())
                                .collect(Collectors.toList()))
                .build();
    }

    private static Function<FullAnswerDto, Answer> getFullAnswerDtoAnswerFunction() {
        return a -> Answer
                .builder()
                .text(a.getText())
                .isRight(a.isRight())
                .build();
    }
}
