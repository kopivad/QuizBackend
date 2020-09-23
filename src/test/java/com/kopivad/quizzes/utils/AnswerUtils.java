package com.kopivad.quizzes.utils;

import com.kopivad.quizzes.domain.Answer;
import com.kopivad.quizzes.domain.db.tables.records.AnswersRecord;
import com.kopivad.quizzes.dto.AnswerDto;
import org.jooq.DSLContext;
import org.jooq.InsertReturningStep;

import java.util.List;
import java.util.Random;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static com.kopivad.quizzes.domain.db.tables.Answers.ANSWERS;
import static org.apache.commons.lang3.math.NumberUtils.INTEGER_ZERO;
import static org.apache.commons.lang3.math.NumberUtils.LONG_ONE;

public class AnswerUtils {
    public static final long TEST_ANSWER_ID = 1L;
    private static final DSLContext DSL_CONTEXT = TestUtils.createTestDefaultDSLContext();
    private static final Random RANDOM = new Random();

    public static void deleteAll() {
        DSL_CONTEXT.deleteFrom(ANSWERS).execute();
    }

    public static List<Answer> generateAnswers(int size) {
        return IntStream.range(INTEGER_ZERO, size)
                .mapToObj(i -> new Answer(
                        i + LONG_ONE,
                        UUID.randomUUID().toString(),
                        RANDOM.nextBoolean(),
                        QuestionUtils.TEST_QUESTION_ID))
                .collect(Collectors.toUnmodifiableList());
    }

    public static Answer generateAnswer() {
        return new Answer(
                LONG_ONE,
                UUID.randomUUID().toString(),
                RANDOM.nextBoolean(),
                QuestionUtils.TEST_QUESTION_ID
        );
    }

    public static List<AnswerDto> generateAnswerDtos(int size) {
        return IntStream.range(INTEGER_ZERO, size)
                .mapToObj(i -> new AnswerDto(
                        UUID.randomUUID().toString(),
                        RANDOM.nextBoolean(),
                        QuestionUtils.TEST_QUESTION_ID
                ))
                .collect(Collectors.toUnmodifiableList());
    }

    public static AnswerDto generateAnswerDto() {
        return new AnswerDto(
                UUID.randomUUID().toString(),
                RANDOM.nextBoolean(),
                QuestionUtils.TEST_QUESTION_ID
        );
    }

    public static long insertRandomAnswerInDb() {
        AnswerDto dto = generateAnswerDto();
        return DSL_CONTEXT
                .insertInto(ANSWERS)
                .set(ANSWERS.BODY, dto.getBody())
                .set(ANSWERS.RIGHT, true)
                .set(ANSWERS.QUESTION_ID, dto.getQuestionId())
                .returning(ANSWERS.ID)
                .fetchOne()
                .getId();
    }

    public static void insertRandomAnswersInDb(int size) {
        List<AnswerDto> dtos = generateAnswerDtos(size);
        List<InsertReturningStep<AnswersRecord>> values = dtos.stream().map(dto -> DSL_CONTEXT
                .insertInto(ANSWERS, ANSWERS.BODY, ANSWERS.RIGHT, ANSWERS.QUESTION_ID)
                .values(dto.getBody(), true, dto.getQuestionId())).collect(Collectors.toUnmodifiableList());

        DSL_CONTEXT.batch(values).execute();
    }

    public static Answer generateAnswer(long id) {
        return new Answer(
                id,
                UUID.randomUUID().toString(),
                RANDOM.nextBoolean(),
                QuestionUtils.TEST_QUESTION_ID
        );
    }

    public static void insertDefaultAnswerInDb() {
        AnswerDto dto = generateAnswerDto();
        DSL_CONTEXT
                .insertInto(ANSWERS)
                .set(ANSWERS.ID, TEST_ANSWER_ID)
                .set(ANSWERS.BODY, dto.getBody())
                .set(ANSWERS.RIGHT, true)
                .set(ANSWERS.QUESTION_ID, dto.getQuestionId())
                .onDuplicateKeyIgnore()
                .execute();
    }

    public static void deleteDefaultAnswer() {
        DSL_CONTEXT.deleteFrom(ANSWERS).where(ANSWERS.ID.eq(TEST_ANSWER_ID)).execute();
    }
}
