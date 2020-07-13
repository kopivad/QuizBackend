package com.kopivad.quizzes.repository.jooq;

import com.kopivad.quizzes.domain.QuizHistory;
import com.kopivad.quizzes.domain.QuizSession;
import com.kopivad.quizzes.domain.User;
import com.kopivad.quizzes.repository.QuizHistoryRepository;
import lombok.RequiredArgsConstructor;
import org.jooq.DSLContext;
import org.jooq.Record;
import org.jooq.RecordMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.kopivad.quizzes.domain.db.tables.QuizHistories.QUIZ_HISTORIES;

@Repository
@RequiredArgsConstructor
public class QuizHistoryRepositoryImpl implements QuizHistoryRepository {
    private final DSLContext dslContext;

    @Override
    public long save(QuizHistory quizHistory) {
        return dslContext
                .insertInto(QUIZ_HISTORIES)
                .set(QUIZ_HISTORIES.RATING, quizHistory.getRating())
                .set(QUIZ_HISTORIES.SESSION_ID, quizHistory.getSession().getId())
                .set(QUIZ_HISTORIES.PDF_FILENAME, quizHistory.getPdfFilename())
                .set(QUIZ_HISTORIES.CSV_FILENAME, quizHistory.getCsvFilename())
                .set(QUIZ_HISTORIES.USER_ID, quizHistory.getUser().getId())
                .set(QUIZ_HISTORIES.TOTAL, quizHistory.getTotal())
                .returning(QUIZ_HISTORIES.ID)
                .fetchOne()
                .getId();
    }

    @Override
    public QuizHistory findById(long id) {
        return dslContext
                .selectFrom(QUIZ_HISTORIES)
                .where(QUIZ_HISTORIES.ID.eq(id))
                .fetchOne()
                .map(getRecordQuizHistoryRecordMapper());
    }

    @Override
    public List<QuizHistory> findAll() {
        return dslContext
                .selectFrom(QUIZ_HISTORIES)
                .fetch()
                .map(getRecordQuizHistoryRecordMapper());
    }

    public RecordMapper<Record, QuizHistory> getRecordQuizHistoryRecordMapper() {
        return r -> QuizHistory
                .builder()
                .id(r.getValue(QUIZ_HISTORIES.ID))
                .rating(r.getValue(QUIZ_HISTORIES.RATING))
                .total(r.getValue(QUIZ_HISTORIES.TOTAL))
                .pdfFilename(r.getValue(QUIZ_HISTORIES.PDF_FILENAME))
                .csvFilename(r.getValue(QUIZ_HISTORIES.CSV_FILENAME))
                .session(QuizSession.builder().id(r.getValue(QUIZ_HISTORIES.SESSION_ID)).build())
                .user(User.builder().id(r.getValue(QUIZ_HISTORIES.USER_ID)).build())
                .build();
    }
}
