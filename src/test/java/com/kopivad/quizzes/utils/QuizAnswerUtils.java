package com.kopivad.quizzes.utils;

import com.kopivad.quizzes.domain.QuizAnswer;
import com.kopivad.quizzes.domain.db.tables.records.QuizAnswersRecord;
import com.kopivad.quizzes.dto.QuizAnswerDto;
import org.jooq.DSLContext;
import org.jooq.InsertReturningStep;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static com.kopivad.quizzes.domain.db.tables.QuizAnswers.QUIZ_ANSWERS;
import static org.apache.commons.lang3.math.NumberUtils.INTEGER_ZERO;
import static org.apache.commons.lang3.math.NumberUtils.LONG_ONE;

public class QuizAnswerUtils {
    private static final DSLContext DSL_CONTEXT = TestUtils.createTestDefaultDSLContext();
    public static final Long TEST_QUIZ_ANSWER_ID = 1L;


    public static QuizAnswerDto generateAnswerDto() {
            return new QuizAnswerDto(
                    QuestionUtils.TEST_QUESTION_ID,
                    QuizSessionUtils.TEST_SESSION_ID,
                    AnswerUtils.TEST_ANSWER_ID
            );
    }

    public static List<QuizAnswerDto> generateAnswerDtos(int size) {
        return IntStream
                .range(INTEGER_ZERO, size)
                .mapToObj(i -> generateAnswerDto())
                .collect(Collectors.toUnmodifiableList());
    }

    public static void deleteAll() {
        DSL_CONTEXT.deleteFrom(QUIZ_ANSWERS).execute();
    }

    public static void insertRandomQuizAnswer(int size) {
        List<QuizAnswerDto> dtos = generateAnswerDtos(size);
        List<InsertReturningStep<QuizAnswersRecord>> values = dtos
                .stream()
                .map(dto -> DSL_CONTEXT
                        .insertInto(QUIZ_ANSWERS, QUIZ_ANSWERS.ANSWER_ID, QUIZ_ANSWERS.QUESTION_ID, QUIZ_ANSWERS.SESSION_ID)
                        .values(dto.getAnswerId(), dto.getQuestionId(), dto.getSessionId()))
                .collect(Collectors.toUnmodifiableList());

        DSL_CONTEXT.batch(values).execute();
    }

    public static List<QuizAnswer> generateQuizAnswers(int size) {
        return IntStream
                .range(INTEGER_ZERO, size)
                .mapToObj(i -> generateQuizAnswer(i + LONG_ONE))
                .collect(Collectors.toUnmodifiableList());
    }

    private static QuizAnswer generateQuizAnswer(long id) {
        return new QuizAnswer(
                id,
                QuestionUtils.TEST_QUESTION_ID,
                QuizSessionUtils.TEST_SESSION_ID,
                AnswerUtils.TEST_ANSWER_ID
        );
    }
}
