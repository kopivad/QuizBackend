package com.kopivad.quizzes.repository.jooq;

import com.kopivad.quizzes.domain.QuizHistory;
import com.kopivad.quizzes.dto.QuizHistoryDto;
import com.kopivad.quizzes.repository.QuizHistoryRepository;
import lombok.RequiredArgsConstructor;
import org.jooq.DSLContext;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

import static com.kopivad.quizzes.domain.db.tables.QuizHistories.QUIZ_HISTORIES;

@Repository
@RequiredArgsConstructor
public class QuizHistoryRepositoryImpl implements QuizHistoryRepository {
    private final DSLContext dslContext;

    @Override
    public long save(QuizHistoryDto quizHistory) {
        return dslContext
                .insertInto(QUIZ_HISTORIES)
                .set(QUIZ_HISTORIES.RATING, quizHistory.getRating())
                .set(QUIZ_HISTORIES.SESSION_ID, quizHistory.getSessionId())
                .set(QUIZ_HISTORIES.PDF_FILE_NAME, quizHistory.getPdfFilename())
                .set(QUIZ_HISTORIES.CSV_FILE_NAME, quizHistory.getCsvFilename())
                .set(QUIZ_HISTORIES.USER_ID, quizHistory.getUserId())
                .set(QUIZ_HISTORIES.TOTAL, quizHistory.getTotal())
                .returning(QUIZ_HISTORIES.ID)
                .fetchOne()
                .getId();
    }

    @Override
    public Optional<QuizHistory> findById(long id) {
        return dslContext
                .selectFrom(QUIZ_HISTORIES)
                .where(QUIZ_HISTORIES.ID.eq(id))
                .fetchOptionalInto(QuizHistory.class);
    }

    @Override
    public List<QuizHistory> findAll() {
        return dslContext
                .selectFrom(QUIZ_HISTORIES)
                .fetchInto(QuizHistory.class);
    }

    @Override
    public List<QuizHistory> findAllBySessionId(long sessionId) {
        return dslContext
                .selectFrom(QUIZ_HISTORIES)
                .fetchInto(QuizHistory.class);
    }
}
