package com.kopivad.quizzes.repository.jooq;

import com.kopivad.quizzes.domain.QuizHistory;
import com.kopivad.quizzes.domain.User;
import com.kopivad.quizzes.repository.QuizHistoryRepository;
import lombok.RequiredArgsConstructor;
import org.jooq.DSLContext;
import org.jooq.Record;
import org.jooq.RecordMapper;
import org.springframework.stereotype.Repository;

import static com.kopivad.quizzes.domain.db.tables.QuizHistories.QUIZ_HISTORIES;

@Repository
@RequiredArgsConstructor
public class QuizHistoryRepositoryImpl implements QuizHistoryRepository {
    private final DSLContext dslContext;

    @Override
    public QuizHistory save(QuizHistory quizHistory) {
        return dslContext
            .insertInto(QUIZ_HISTORIES)
            .set(QUIZ_HISTORIES.USER_ID, quizHistory.getUser().getId())
            .set(QUIZ_HISTORIES.TOTAL, quizHistory.getTotal())
            .returning(QUIZ_HISTORIES.fields())
            .fetchOne()
            .map(getQuizHistoryRecordMapper());
    }

    private RecordMapper<Record, QuizHistory> getQuizHistoryRecordMapper() {
        return r -> QuizHistory
            .builder()
            .id(r.getValue(QUIZ_HISTORIES.ID))
            .user(User.builder().id(r.getValue(QUIZ_HISTORIES.USER_ID)).build())
            .build();
    }
}
