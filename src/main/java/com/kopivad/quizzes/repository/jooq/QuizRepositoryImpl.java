package com.kopivad.quizzes.repository.jooq;

import com.kopivad.quizzes.domain.Quiz;
import com.kopivad.quizzes.domain.User;
import com.kopivad.quizzes.repository.QuizRepository;
import lombok.RequiredArgsConstructor;
import org.jooq.DSLContext;
import org.jooq.Record;
import org.jooq.RecordMapper;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.util.List;

import static com.kopivad.quizzes.domain.db.tables.Quizzes.QUIZZES;

@Repository
@RequiredArgsConstructor
public class QuizRepositoryImpl implements QuizRepository {
    private final DSLContext dslContext;

    @Override
    public List<Quiz> findAll() {
        return dslContext
                .selectFrom(QUIZZES)
                .fetch()
                .map(getQuizFromRecordMapper());
    }

    @Override
    public Quiz findById(Long id) {
        return dslContext
                .selectFrom(QUIZZES)
                .where(QUIZZES.ID.eq(id))
                .fetchOne()
                .map(getQuizFromRecordMapper());
    }

    @Override
    public Quiz save(Quiz quiz) {
        return dslContext
                .insertInto(QUIZZES)
                .set(QUIZZES.TITLE, quiz.getTitle())
                .set(QUIZZES.ACTIVE, quiz.isActive())
                .set(QUIZZES.DESCRIPTION, quiz.getDescription())
                .set(QUIZZES.AUTHOR_ID, quiz.getAuthor().getId())
                .set(QUIZZES.CREATION_DATE, Timestamp.valueOf(quiz.getCreationDate()))
                .returning(QUIZZES.fields())
                .fetchOne()
                .map(getQuizFromRecordMapper());
    }

    @Override
    public Quiz update(Long id, Quiz quiz) {
        return dslContext
                .update(QUIZZES)
                .set(QUIZZES.TITLE, quiz.getTitle())
                .set(QUIZZES.DESCRIPTION, quiz.getDescription())
                .set(QUIZZES.ACTIVE, quiz.isActive())
                .set(QUIZZES.AUTHOR_ID, quiz.getAuthor().getId())
                .where(QUIZZES.ID.eq(id))
                .returningResult(QUIZZES.fields())
                .fetchOne()
                .map(getQuizFromRecordMapper());
    }

    @Override
    public void delete(Long id) {
        dslContext
                .deleteFrom(QUIZZES)
                .where(QUIZZES.ID.eq(id))
                .execute();
    }

    private RecordMapper<Record, Quiz> getQuizFromRecordMapper() {
        return record -> Quiz
                .builder()
                .id(record.getValue(QUIZZES.ID))
                .title(record.getValue(QUIZZES.TITLE))
                .description(record.getValue(QUIZZES.DESCRIPTION))
                .author(User.builder().id(record.getValue(QUIZZES.AUTHOR_ID)).build())
                .active(record.getValue(QUIZZES.ACTIVE))
                .creationDate(record.getValue(QUIZZES.CREATION_DATE).toLocalDateTime())
                .build();
    }
}
