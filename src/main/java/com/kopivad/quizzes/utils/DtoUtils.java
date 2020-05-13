package com.kopivad.quizzes.utils;

import com.kopivad.quizzes.domain.*;
import com.kopivad.quizzes.dto.*;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

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
                .body(answerDto.getText())
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
                .questions(getQuizQuestions(dto))
                .build();
    }

    private static List<Question> getQuizQuestions(FullQuizDto dto) {
        return dto.getQuestions()
                .stream()
                .map(getFullQuestionDtoQuestionFunction())
                .collect(Collectors.toUnmodifiableList());
    }

    private static Function<FullQuestionDto, Question> getFullQuestionDtoQuestionFunction() {
        return q -> Question
                .builder()
                .type(q.getType())
                .title(q.getTitle())
                .answers(getQuestionAnswers(q))
                .build();
    }

    private static List<Answer> getQuestionAnswers(FullQuestionDto q) {
        return q.getAnswers()
                .stream()
                .map(getFullAnswerDtoAnswerFunction())
                .collect(Collectors.toUnmodifiableList());
    }

    private static Function<FullAnswerDto, Answer> getFullAnswerDtoAnswerFunction() {
        return a -> Answer
                .builder()
                .body(a.getText())
                .isRight(a.isRight())
                .build();
    }
}
