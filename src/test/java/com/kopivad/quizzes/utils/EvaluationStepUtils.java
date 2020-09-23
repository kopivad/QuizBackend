package com.kopivad.quizzes.utils;

import com.kopivad.quizzes.domain.EvaluationStep;
import com.kopivad.quizzes.domain.db.tables.records.StepsRecord;
import com.kopivad.quizzes.dto.EvaluationStepDto;
import org.jooq.DSLContext;
import org.jooq.InsertReturningStep;

import java.util.List;
import java.util.Random;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static com.kopivad.quizzes.domain.db.tables.Steps.STEPS;
import static org.apache.commons.lang3.math.NumberUtils.INTEGER_ZERO;

public class EvaluationStepUtils {
    private final static DSLContext DSL_CONTEXT = TestUtils.createTestDefaultDSLContext();
    private final static Random RANDOM = new Random();
    public final static Long TEST_STEP_ID = 1L;

    public static List<EvaluationStepDto> generateStepDtos(int size) {
        return IntStream
                .range(INTEGER_ZERO, size)
                .mapToObj(i -> generateStepDto())
                .collect(Collectors.toUnmodifiableList());
    }

    public static EvaluationStepDto generateStepDto() {
        return new EvaluationStepDto(
                RANDOM.nextInt(100),
                RANDOM.nextInt(100),
                UUID.randomUUID().toString(),
                QuizUtils.TEST_QUIZ_ID
        );
    }

    public static void deleteAll() {
        DSL_CONTEXT.deleteFrom(STEPS).execute();
    }

    public static int insertStepsWithSameQuizId() {
        int bound = 10;
        List<EvaluationStepDto> dtos = generateStepDtos(RANDOM.nextInt(bound));
        List<InsertReturningStep<StepsRecord>> values = dtos
                .stream()
                .map(dto -> DSL_CONTEXT
                        .insertInto(STEPS, STEPS.RATING, STEPS.MIN_TOTAL, STEPS.MAX_TOTAL, STEPS.QUIZ_ID)
                        .values(UUID.randomUUID().toString(), RANDOM.nextInt(), RANDOM.nextInt(), QuizUtils.TEST_QUIZ_ID))
                .collect(Collectors.toUnmodifiableList());

        return DSL_CONTEXT.batch(
                values
        ).execute().length;
    }

    public static List<EvaluationStep> generateSteps(int size) {
        return IntStream
                .range(INTEGER_ZERO, size)
                .mapToObj(i -> new EvaluationStep(
                        TEST_STEP_ID,
                        RANDOM.nextInt(),
                        RANDOM.nextInt(),
                        UUID.randomUUID().toString(),
                        QuizUtils.TEST_QUIZ_ID))
                .collect(Collectors.toUnmodifiableList());
    }
}
