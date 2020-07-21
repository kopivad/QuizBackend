package com.kopivad.quizzes.utils;

import com.kopivad.quizzes.domain.Quiz;
import com.kopivad.quizzes.domain.QuizSession;
import com.kopivad.quizzes.domain.User;
import com.kopivad.quizzes.dto.QuizSessionDto;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.apache.commons.lang3.math.NumberUtils.INTEGER_ZERO;
import static org.apache.commons.lang3.math.NumberUtils.LONG_ONE;

public class QuizSessionUtils {
    public static List<QuizSession> generateQuizSessions(int size) {
        return IntStream.range(INTEGER_ZERO, size)
                .mapToObj(i -> generateQuizSession()
                        .toBuilder()
                        .id(i + LONG_ONE)
                        .build())
                .collect(Collectors.toUnmodifiableList());
    }

    public static QuizSession generateQuizSession() {
        return QuizSession
                .builder()
                .id(LONG_ONE)
                .date(LocalDateTime.now())
                .user(UserUtils.generateUser())
                .quiz(QuizUtils.generateQuizWithFullData())
                .build();
    }

    public static QuizSessionDto generateQuizSessionDto() {
        return QuizSessionDto
                .builder()
                .id(LONG_ONE)
                .date(LocalDateTime.now())
                .userId(UserUtils.generateUser().getId())
                .quizId(QuizUtils.generateQuiz().getId())
                .build();
    }
}
