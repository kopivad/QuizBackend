package com.kopivad.quizzes.utils;

import com.kopivad.quizzes.domain.Quiz;
import com.kopivad.quizzes.domain.db.tables.records.QuizzesRecord;
import com.kopivad.quizzes.dto.QuizDto;
import org.jooq.DSLContext;
import org.jooq.InsertReturningStep;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Random;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static com.kopivad.quizzes.domain.db.tables.Quizzes.QUIZZES;
import static org.apache.commons.lang3.math.NumberUtils.INTEGER_ZERO;
import static org.apache.commons.lang3.math.NumberUtils.LONG_ONE;

public class QuizUtils {
    private final static DSLContext DSL_CONTEXT = TestUtils.createTestDefaultDSLContext();
    private final static Random RANDOM = new Random();
    public final static Long TEST_QUIZ_ID = 1L;

    public static void insertDefaultQuiz() {
        QuizDto dto = generateQuizDto();
        DSL_CONTEXT
                .insertInto(QUIZZES)
                .set(QUIZZES.ID, TEST_QUIZ_ID)
                .set(QUIZZES.TITLE, dto.getTitle())
                .set(QUIZZES.DESCRIPTION, dto.getDescription())
                .set(QUIZZES.TOTAL, dto.getTotal())
                .set(QUIZZES.ACTIVE, RANDOM.nextBoolean())
                .set(QUIZZES.CREATION_DATE, Timestamp.valueOf(LocalDateTime.now()))
                .set(QUIZZES.AUTHOR_ID, UserUtils.TEST_USER_ID)
                .onDuplicateKeyIgnore()
                .execute();
    }

    public static QuizDto generateQuizDto() {
        return new QuizDto(
                UUID.randomUUID().toString(),
                UUID.randomUUID().toString(),
                RANDOM.nextInt(100),
                UserUtils.TEST_USER_ID,
                EvaluationStepUtils.generateStepDtos(RANDOM.nextInt(10)),
                QuestionUtils.generateQuestionDtos(RANDOM.nextInt(10))
        );
    }

    public static void deleteAll() {
        DSL_CONTEXT.deleteFrom(QUIZZES).execute();
    }

    public static int insertRandomQuizzes(int size) {
        List<QuizDto> dtos = generateQuizDtos(size);
        List<InsertReturningStep<QuizzesRecord>> values = dtos
                .stream()
                .map(dto -> DSL_CONTEXT
                        .insertInto(QUIZZES, QUIZZES.TITLE, QUIZZES.DESCRIPTION, QUIZZES.ACTIVE, QUIZZES.TOTAL, QUIZZES.CREATION_DATE, QUIZZES.AUTHOR_ID)
                        .values(dto.getTitle(), dto.getDescription(), RANDOM.nextBoolean(), dto.getTotal(), Timestamp.valueOf(LocalDateTime.now()), UserUtils.TEST_USER_ID)
                ).collect(Collectors.toUnmodifiableList());

        return DSL_CONTEXT.batch(values).execute().length;
    }

    private static List<QuizDto> generateQuizDtos(int size) {
        return IntStream.range(INTEGER_ZERO, size)
                .mapToObj(i -> new QuizDto(
                        UUID.randomUUID().toString(),
                        UUID.randomUUID().toString(),
                        RANDOM.nextInt(),
                        UserUtils.TEST_USER_ID,
                        EvaluationStepUtils.generateStepDtos(RANDOM.nextInt(10)),
                        QuestionUtils.generateQuestionDtos(RANDOM.nextInt(10))
                ))
                .collect(Collectors.toUnmodifiableList());
    }

    public static long insertRandomUserInDb() {
        QuizDto dto = generateQuizDto();
        return DSL_CONTEXT
                .insertInto(QUIZZES)
                .set(QUIZZES.TITLE, dto.getTitle())
                .set(QUIZZES.DESCRIPTION, dto.getDescription())
                .set(QUIZZES.TOTAL, dto.getTotal())
                .set(QUIZZES.ACTIVE, RANDOM.nextBoolean())
                .set(QUIZZES.CREATION_DATE, Timestamp.valueOf(LocalDateTime.now()))
                .set(QUIZZES.AUTHOR_ID, UserUtils.TEST_USER_ID)
                .returning(QUIZZES.ID)
                .fetchOne()
                .getId();
    }

    public static Quiz generateQuiz(long id) {
        return new Quiz(
                id,
                UUID.randomUUID().toString(),
                UUID.randomUUID().toString(),
                RANDOM.nextInt(100),
                RANDOM.nextBoolean(),
                LocalDateTime.now(),
                UserUtils.TEST_USER_ID
        );
    }

    public static Quiz generateQuiz() {
        return new Quiz(
                TEST_QUIZ_ID,
                UUID.randomUUID().toString(),
                UUID.randomUUID().toString(),
                RANDOM.nextInt(100),
                RANDOM.nextBoolean(),
                LocalDateTime.now(),
                UserUtils.TEST_USER_ID
        );
    }

    public static List<QuizDto> generateQuizDtoWithSimilarTitle(String prefix, int size) {
        return IntStream.range(INTEGER_ZERO, size)
                .mapToObj(i -> new QuizDto(
                        prefix + UUID.randomUUID().toString(),
                        UUID.randomUUID().toString(),
                        RANDOM.nextInt(),
                        UserUtils.TEST_USER_ID,
                        EvaluationStepUtils.generateStepDtos(RANDOM.nextInt(10)),
                        QuestionUtils.generateQuestionDtos(RANDOM.nextInt(10))
                ))
                .collect(Collectors.toUnmodifiableList());
    }

    public static void insertRandomQuizzesWithSimilarTitle(String prefix, int size) {
        List<QuizDto> dtos = generateQuizDtoWithSimilarTitle(prefix, size);
        List<InsertReturningStep<QuizzesRecord>> values = dtos
                .stream()
                .map(dto -> DSL_CONTEXT
                        .insertInto(QUIZZES, QUIZZES.TITLE, QUIZZES.DESCRIPTION, QUIZZES.ACTIVE, QUIZZES.TOTAL, QUIZZES.CREATION_DATE, QUIZZES.AUTHOR_ID)
                        .values(dto.getTitle(), dto.getDescription(), true, dto.getTotal(), Timestamp.valueOf(LocalDateTime.now()), dto.getAuthorId())
                ).collect(Collectors.toUnmodifiableList());

        DSL_CONTEXT.batch(values).execute();
    }

    public static List<Quiz> generateQuizzes(int size) {
        return IntStream
                .range(INTEGER_ZERO, size)
                .mapToObj(i -> generateQuiz(i + LONG_ONE))
                .collect(Collectors.toUnmodifiableList());
    }
}
