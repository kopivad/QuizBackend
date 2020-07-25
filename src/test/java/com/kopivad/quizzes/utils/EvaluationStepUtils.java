package com.kopivad.quizzes.utils;

import com.kopivad.quizzes.domain.EvaluationStep;
import com.kopivad.quizzes.domain.Quiz;
import com.kopivad.quizzes.dto.EvaluationStepDto;
import io.codearte.jfairy.Fairy;
import io.codearte.jfairy.producer.text.TextProducer;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.apache.commons.lang3.math.NumberUtils.*;

public class EvaluationStepUtils {
    public static EvaluationStep generateStep() {
        Fairy fairy = Fairy.create();
        TextProducer producer = fairy.textProducer();
        return EvaluationStep
                .builder()
                .id(LONG_ONE)
                .minTotal(INTEGER_ZERO)
                .maxTotal(INTEGER_TWO)
                .rating(producer.word(INTEGER_ONE))
                .quiz(QuizUtils.generateQuiz())
                .build();
    }

    public static List<EvaluationStep> generateSteps(long quizId, int size) {
        return IntStream
                .range(INTEGER_ZERO, size)
                .mapToObj(i -> generateStep()
                        .toBuilder()
                        .id(i+ LONG_ONE)
                        .quiz(Quiz.builder().id(quizId).build())
                        .build())
                .collect(Collectors.toUnmodifiableList());
    }

    public static EvaluationStepDto generateStepDto() {
        Fairy fairy = Fairy.create();
        TextProducer producer = fairy.textProducer();
        return EvaluationStepDto
                .builder()
                .id(LONG_ONE)
                .minTotal(INTEGER_ZERO)
                .maxTotal(INTEGER_TWO)
                .rating(producer.word(INTEGER_ONE))
                .quizId(QuizUtils.generateQuiz().getId())
                .build();
    }

    public static List<EvaluationStepDto> generateStepDtos(int stepsCount) {
        return IntStream
                .range(INTEGER_ZERO, stepsCount)
                .mapToObj(i -> generateStepDto().toBuilder().id(i + LONG_ONE).build())
                .collect(Collectors.toUnmodifiableList());
    }

    public static List<EvaluationStep> generateSteps(int size) {
        return IntStream
                .range(INTEGER_ZERO, size)
                .mapToObj(i -> generateStep().toBuilder().id(i + LONG_ONE).build())
                .collect(Collectors.toUnmodifiableList());
    }
}
