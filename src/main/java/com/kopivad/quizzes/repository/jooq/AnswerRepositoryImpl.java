package com.kopivad.quizzes.repository.jooq;

import com.kopivad.quizzes.domain.Answer;
import com.kopivad.quizzes.repository.AnswerRepository;
import lombok.RequiredArgsConstructor;
import org.jooq.DSLContext;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.kopivad.quizzes.domain.db.tables.Answers.ANSWERS;
import static com.kopivad.quizzes.repository.jooq.RecordMappers.getAnswerFromRecordMapper;
import static org.apache.commons.lang3.math.NumberUtils.INTEGER_ZERO;

@Repository
@RequiredArgsConstructor
public class AnswerRepositoryImpl implements AnswerRepository {
    private final DSLContext dslContext;

    @Override
    public List<Answer> findAll() {
        return dslContext
                .selectFrom(ANSWERS)
                .fetch()
                .map(getAnswerFromRecordMapper());
    }

    @Override
    public Answer findById(Long id) {
        return dslContext
                .selectFrom(ANSWERS)
                .where(ANSWERS.ID.eq(id))
                .fetchOne()
                .map(getAnswerFromRecordMapper());
    }

    @Override
    public long save(Answer answer) {
        return dslContext
                .insertInto(ANSWERS)
                .set(ANSWERS.BODY, answer.getBody())
                .set(ANSWERS.IS_RIGHT, answer.isRight())
                .set(ANSWERS.QUESTION_ID, answer.getQuestion().getId())
                .returning(ANSWERS.ID)
                .fetchOne()
                .getId();
    }

    @Override
    public boolean update(Answer answer) {
        int affectedRows = dslContext
                .update(ANSWERS)
                .set(ANSWERS.BODY, answer.getBody())
                .set(ANSWERS.IS_RIGHT, answer.isRight())
                .set(ANSWERS.QUESTION_ID, answer.getQuestion().getId())
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
                .fetch()
                .map(getAnswerFromRecordMapper());
    }
}
