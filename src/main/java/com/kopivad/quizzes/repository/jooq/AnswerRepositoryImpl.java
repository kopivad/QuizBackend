package com.kopivad.quizzes.repository.jooq;

import com.kopivad.quizzes.domain.Answer;
import com.kopivad.quizzes.repository.AnswerRepository;
import lombok.RequiredArgsConstructor;
import org.jooq.DSLContext;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

import static com.kopivad.quizzes.domain.db.tables.Answers.ANSWERS;
import static org.apache.commons.lang3.math.NumberUtils.INTEGER_ZERO;

@Repository
@RequiredArgsConstructor
public class AnswerRepositoryImpl implements AnswerRepository {
    private final DSLContext dslContext;

    @Override
    public List<Answer> findAll() {
        return dslContext
                .selectFrom(ANSWERS)
                .fetchInto(Answer.class);
    }

    @Override
    public Optional<Answer> findById(Long id) {
        return dslContext
                .selectFrom(ANSWERS)
                .where(ANSWERS.ID.eq(id))
                .fetchOptionalInto(Answer.class);
    }

    @Override
    public boolean save(Answer answer) {
        int affectedRows = dslContext
                .insertInto(ANSWERS)
                .set(ANSWERS.BODY, answer.getBody())
                .set(ANSWERS.RIGHT, answer.isRight())
                .set(ANSWERS.QUESTION_ID, answer.getQuestionId())
                .execute();

        return affectedRows > INTEGER_ZERO;
    }

    @Override
    public boolean update(Answer answer) {
        int affectedRows = dslContext
                .update(ANSWERS)
                .set(ANSWERS.BODY, answer.getBody())
                .set(ANSWERS.RIGHT, answer.isRight())
                .where(ANSWERS.ID.eq(answer.getId()))
                .execute();

        return affectedRows > INTEGER_ZERO;
    }

    @Override
    public boolean delete(Long id) {
        int affectedRows = dslContext
                .deleteFrom(ANSWERS)
                .where(ANSWERS.ID.eq(id))
                .execute();

        return affectedRows > INTEGER_ZERO;
    }

    @Override
    public List<Answer> findByQuestionId(Long id) {
        return dslContext
                .selectFrom(ANSWERS)
                .where(ANSWERS.QUESTION_ID.eq(id))
                .fetchInto(Answer.class);
    }
}
