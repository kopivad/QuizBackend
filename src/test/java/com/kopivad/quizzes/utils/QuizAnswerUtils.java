package com.kopivad.quizzes.utils;

import com.kopivad.quizzes.domain.QuizAnswer;
import com.kopivad.quizzes.domain.QuizSession;
import com.kopivad.quizzes.dto.QuizAnswerDto;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.apache.commons.lang3.math.NumberUtils.INTEGER_ZERO;
import static org.apache.commons.lang3.math.NumberUtils.LONG_ONE;

public class QuizAnswerUtils {
    public static QuizAnswer generateAnswer() {
        return QuizAnswer
                .builder()
                .id(LONG_ONE)
                .session(QuizSessionUtils.generateQuizSession())
                .answer(AnswerUtils.generateAnswer())
                .question(QuestionUtils.generateQuestion())
                .build();
    }

    public static List<QuizAnswer> generateAnswersWithSessionId(long sessionId, int size) {
        return IntStream
                .range(INTEGER_ZERO, size)
                .mapToObj(i -> generateAnswer()
                        .toBuilder()
                        .id(i + LONG_ONE)
                        .session(QuizSession.builder().id(sessionId).build())
                        .build())
                .collect(Collectors.toUnmodifiableList());
    }

    public static QuizAnswerDto generateAnswerDto() {
            return QuizAnswerDto
                    .builder()
                    .id(LONG_ONE)
                    .sessionId(QuizSessionUtils.generateQuizSession().getId())
                    .answerId(AnswerUtils.generateAnswer().getId())
                    .questionId(QuestionUtils.generateQuestion().getId())
                    .build();
    }

    public static List<QuizAnswer> generateAnswers(int count) {
        return IntStream
                .range(INTEGER_ZERO, count)
                .mapToObj(i -> generateAnswer()
                        .toBuilder()
                        .id(i + LONG_ONE)
                        .build())
                .collect(Collectors.toUnmodifiableList());
    }

    public static List<QuizAnswerDto> generateAnswerDtos(int count) {
        return IntStream
                .range(INTEGER_ZERO, count)
                .mapToObj(i -> generateAnswerDto()
                        .toBuilder()
                        .id(i + LONG_ONE)
                        .build())
                .collect(Collectors.toUnmodifiableList());
    }
}
