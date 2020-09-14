package com.kopivad.quizzes.repository.jooq;

import com.kopivad.quizzes.domain.QuizSession;
import com.kopivad.quizzes.repository.QuizSessionRepository;
import lombok.RequiredArgsConstructor;
import org.jooq.DSLContext;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.util.Optional;

import static com.kopivad.quizzes.domain.db.tables.QuizSessions.QUIZ_SESSIONS;
import static org.apache.commons.lang3.math.NumberUtils.INTEGER_ZERO;

@Repository
@RequiredArgsConstructor
public class QuizSessionRepositoryImpl implements QuizSessionRepository {
    private final DSLContext dslContext;

    @Override
    public long save(QuizSession quizSession) {
        return dslContext
                .insertInto(QUIZ_SESSIONS)
                .set(QUIZ_SESSIONS.USER_ID, quizSession.getUserId())
                .set(QUIZ_SESSIONS.QUIZ_ID, quizSession.getQuizId())
                .set(QUIZ_SESSIONS.CREATION_DATE, Timestamp.valueOf(quizSession.getDate()))
                .returning(QUIZ_SESSIONS.ID)
                .fetchOne()
                .getId();
    }

    @Override
    public boolean update(QuizSession quizSession) {
        int affectedRows = dslContext
                .update(QUIZ_SESSIONS)
                .set(QUIZ_SESSIONS.USER_ID, quizSession.getUserId())
                .set(QUIZ_SESSIONS.QUIZ_ID, quizSession.getQuizId())
                .set(QUIZ_SESSIONS.CREATION_DATE, Timestamp.valueOf(quizSession.getDate()))
                .where(QUIZ_SESSIONS.ID.eq(quizSession.getId()))
                .execute();

        return affectedRows > INTEGER_ZERO;
    }

    @Override
    public Optional<QuizSession> findById(long sessionId) {
        return dslContext
                .selectFrom(QUIZ_SESSIONS)
                .where(QUIZ_SESSIONS.ID.eq(sessionId))
                .fetchOptionalInto(QuizSession.class);
    }
}
