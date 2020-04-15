package com.kopivad.quizzes.repository.jooq;

import com.kopivad.quizzes.domain.Answer;
import com.kopivad.quizzes.repository.AnswerRepository;
import lombok.RequiredArgsConstructor;
import org.jooq.DSLContext;
import org.jooq.Record;
import org.jooq.RecordMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.kopivad.quizzes.domain.db.tables.Answers.ANSWERS;

@Repository("jooqAnswerRepository")
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
    public Answer save(Answer answer) {
        return dslContext
                .insertInto(ANSWERS, ANSWERS.TEXT, ANSWERS.IS_RIGHT, ANSWERS.QUESTION_ID)
                .values(answer.getText(), answer.isRight(), answer.getQuestion().getId())
                .returning(ANSWERS.ID, ANSWERS.TEXT, ANSWERS.IS_RIGHT, ANSWERS.QUESTION_ID)
                .fetchOne()
                .map(getAnswerFromRecordMapper());
    }

    @Override
    public Answer update(Long id, Answer answer) {
        return dslContext
                .update(ANSWERS)
                .set(ANSWERS.TEXT, answer.getText())
                .set(ANSWERS.IS_RIGHT, answer.isRight())
                .set(ANSWERS.QUESTION_ID, answer.getQuestion().getId())
                .where(ANSWERS.ID.eq(id))
                .returningResult(ANSWERS.ID, ANSWERS.TEXT, ANSWERS.IS_RIGHT, ANSWERS.QUESTION_ID)
                .fetchOne()
                .map(getAnswerFromRecordMapper());
    }

    @Override
    public void delete(Long id) {
        dslContext
                .deleteFrom(ANSWERS)
                .where(ANSWERS.ID.eq(id))
                .execute();
    }

    private RecordMapper<Record, Answer> getAnswerFromRecordMapper() {
        return record -> Answer
                .builder()
                .id(record.getValue(ANSWERS.ID))
                .text(record.getValue(ANSWERS.TEXT))
                .isRight(record.getValue(ANSWERS.IS_RIGHT))
                .build();
    }
}
