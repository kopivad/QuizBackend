package com.kopivad.quizzes.repository.jooq;

import com.kopivad.quizzes.domain.Quiz;
import com.kopivad.quizzes.domain.QuizSession;
import com.kopivad.quizzes.domain.User;
import com.kopivad.quizzes.repository.QuizSessionRepository;
import lombok.RequiredArgsConstructor;
import org.jooq.DSLContext;
import org.jooq.Record;
import org.jooq.RecordMapper;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;

import static com.kopivad.quizzes.domain.db.tables.QuizSessions.QUIZ_SESSIONS;

@Repository
@RequiredArgsConstructor
public class QuizSessionRepositoryImpl implements QuizSessionRepository {
    private final DSLContext dslContext;

    @Override
    public long save(QuizSession quizSession) {
        return dslContext
                .insertInto(QUIZ_SESSIONS)
                .set(QUIZ_SESSIONS.USER_ID, quizSession.getUser().getId())
                .set(QUIZ_SESSIONS.QUIZ_ID, quizSession.getQuiz().getId())
                .set(QUIZ_SESSIONS.DATE, Timestamp.valueOf(quizSession.getDate()))
                .returning(QUIZ_SESSIONS.ID)
                .fetchOne()
                .getId();
    }

    @Override
    public QuizSession update(QuizSession quizSession) {
        return dslContext
                .update(QUIZ_SESSIONS)
                .set(QUIZ_SESSIONS.USER_ID, quizSession.getUser().getId())
                .set(QUIZ_SESSIONS.QUIZ_ID, quizSession.getQuiz().getId())
                .set(QUIZ_SESSIONS.DATE, Timestamp.valueOf(quizSession.getDate()))
                .where(QUIZ_SESSIONS.ID.eq(quizSession.getId()))
                .returning(QUIZ_SESSIONS.fields())
                .fetchOne()
                .map(getQuizSessionRecordMapper());
    }

    private RecordMapper<Record, QuizSession> getQuizSessionRecordMapper() {
        return r -> QuizSession
                .builder()
                .id(r.getValue(QUIZ_SESSIONS.ID))
                .date(r.getValue(QUIZ_SESSIONS.DATE).toLocalDateTime())
                .quiz(Quiz.builder().id(r.getValue(QUIZ_SESSIONS.QUIZ_ID)).build())
                .user(User.builder().id(r.getValue(QUIZ_SESSIONS.USER_ID)).build())
                .build();
    }
}
