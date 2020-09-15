package com.kopivad.quizzes.repository.jooq;

import com.kopivad.quizzes.domain.Question;
import com.kopivad.quizzes.repository.QuestionRepository;
import lombok.RequiredArgsConstructor;
import org.jooq.DSLContext;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

import static com.kopivad.quizzes.domain.db.tables.Questions.QUESTIONS;
import static org.apache.commons.lang3.math.NumberUtils.INTEGER_ZERO;

@Repository
@RequiredArgsConstructor
public class QuestionRepositoryImpl implements QuestionRepository {
    private final DSLContext dslContext;

    @Override
    public List<Question> findAll() {
        return dslContext
                .selectFrom(QUESTIONS)
                .fetchInto(Question.class);
    }

    @Override
    public Optional<Question> findById(Long id) {
        return dslContext
                .selectFrom(QUESTIONS)
                .where(QUESTIONS.ID.eq(id))
                .fetchOptionalInto(Question.class);
    }

    @Override
    public long save(Question question) {
        return dslContext
                .insertInto(QUESTIONS)
                .set(QUESTIONS.TITLE, question.getTitle())
                .set(QUESTIONS.TYPE, question.getType().name())
                .set(QUESTIONS.VALUE, question.getValue())
                .set(QUESTIONS.QUIZ_ID, question.getQuizId())
                .returning(QUESTIONS.ID)
                .fetchOne()
                .getId();
    }

    @Override
    public boolean update(Question question) {
        int affectedRows = dslContext
                .update(QUESTIONS)
                .set(QUESTIONS.TITLE, question.getTitle())
                .set(QUESTIONS.TYPE, question.getType().name())
                .set(QUESTIONS.VALUE, question.getValue())
                .where(QUESTIONS.ID.eq(question.getId()))
                .execute();

        return affectedRows > INTEGER_ZERO;
    }

    @Override
    public boolean delete(Long id) {
        int affectedRows = dslContext
                .deleteFrom(QUESTIONS)
                .where(QUESTIONS.ID.eq(id))
                .execute();
        return affectedRows > INTEGER_ZERO;
    }

    @Override
    public List<Question> findByQuizId(Long id) {
        return dslContext
                .selectFrom(QUESTIONS)
                .where(QUESTIONS.QUIZ_ID.eq(id))
                .fetchInto(Question.class);
    }
}
