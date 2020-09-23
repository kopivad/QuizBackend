package com.kopivad.quizzes.utils;

import com.kopivad.quizzes.domain.QuizSession;
import com.kopivad.quizzes.dto.QuizSessionDto;
import org.jooq.DSLContext;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Collections;

import static com.kopivad.quizzes.domain.db.tables.QuizSessions.QUIZ_SESSIONS;

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

    public static void deleteDefaultQuizSession() {
        DSL_CONTEXT.deleteFrom(QUIZ_SESSIONS).where(QUIZ_SESSIONS.ID.eq(TEST_SESSION_ID)).execute();
    }
}
