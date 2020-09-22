package com.kopivad.quizzes.utils;

import com.kopivad.quizzes.domain.QuizSession;
import com.kopivad.quizzes.domain.db.tables.records.QuizSessionsRecord;
import com.kopivad.quizzes.dto.QuizSessionDto;
import org.jooq.DSLContext;
import org.jooq.InsertReturningStep;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static com.kopivad.quizzes.domain.db.tables.QuizSessions.QUIZ_SESSIONS;
import static org.apache.commons.lang3.math.NumberUtils.INTEGER_ZERO;

public class QuizSessionUtils {
    private final static DSLContext DSL_CONTEXT = TestUtils.createTestDefaultDSLContext();
    public final static Long TEST_SESSION_ID = 1L;

    public static QuizSessionDto generateQuizSessionDto() {
        return new QuizSessionDto(
                QuizUtils.TEST_QUIZ_ID,
                UserUtils.TEST_USER_ID,
                Collections.emptyList(),
                Collections.emptyList()
        );
    }

    public static QuizSession generateQuizSession(long id) {
        return new QuizSession(
                id,
                QuizUtils.TEST_QUIZ_ID,
                UserUtils.TEST_USER_ID,
                LocalDateTime.now()
        );
    }

    public static long insertRandomQuizSession() {
        QuizSessionDto dto = generateQuizSessionDto();
        return DSL_CONTEXT
                .insertInto(QUIZ_SESSIONS, QUIZ_SESSIONS.CREATION_DATE, QUIZ_SESSIONS.QUIZ_ID, QUIZ_SESSIONS.USER_ID)
                .values(Timestamp.valueOf(LocalDateTime.now()), dto.getQuizId(), dto.getUserId())
                .returning(QUIZ_SESSIONS.ID)
                .fetchOne()
                .getId();
    }

    public static void deleteAll() {
        DSL_CONTEXT.deleteFrom(QUIZ_SESSIONS);
    }

    public static void insertDefaultQuizSession() {
        DSL_CONTEXT
                .insertInto(QUIZ_SESSIONS, QUIZ_SESSIONS.ID, QUIZ_SESSIONS.CREATION_DATE, QUIZ_SESSIONS.QUIZ_ID, QUIZ_SESSIONS.USER_ID)
                .values(TEST_SESSION_ID, Timestamp.valueOf(LocalDateTime.now()), QuizUtils.TEST_QUIZ_ID, UserUtils.TEST_USER_ID)
                .onDuplicateKeyIgnore()
                .execute();
    }

    public static void insertRandomQuizSessions(int size) {
        List<QuizSessionDto> dtos = generateQuizSessionDtos(size);
        List<InsertReturningStep<QuizSessionsRecord>> values = dtos
                .stream()
                .map(dto -> DSL_CONTEXT
                        .insertInto(QUIZ_SESSIONS, QUIZ_SESSIONS.CREATION_DATE, QUIZ_SESSIONS.QUIZ_ID, QUIZ_SESSIONS.USER_ID)
                        .values(Timestamp.valueOf(LocalDateTime.now()), dto.getQuizId(), dto.getUserId()))
                .collect(Collectors.toUnmodifiableList());

        DSL_CONTEXT.batch(values).execute();
    }

    private static List<QuizSessionDto> generateQuizSessionDtos(int size) {
        return IntStream
                .range(INTEGER_ZERO, size)
                .mapToObj(i -> new QuizSessionDto(
                        QuizUtils.TEST_QUIZ_ID,
                        UserUtils.TEST_USER_ID,
                        Collections.emptyList(),
                        Collections.emptyList()))
                .collect(Collectors.toUnmodifiableList());
    }
}
