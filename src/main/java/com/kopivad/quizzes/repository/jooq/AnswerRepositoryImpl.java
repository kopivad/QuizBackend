package com.kopivad.quizzes.repository.jooq;

import com.kopivad.quizzes.domain.Answer;
import com.kopivad.quizzes.domain.db.tables.records.AnswersRecord;
import com.kopivad.quizzes.dto.AnswerDto;
import com.kopivad.quizzes.repository.AnswerRepository;
import lombok.RequiredArgsConstructor;
import org.jooq.DSLContext;
import org.jooq.InsertValuesStep3;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.kopivad.quizzes.domain.db.tables.Answers.ANSWERS;

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
    public int save(AnswerDto answer) {
        return dslContext
                .insertInto(ANSWERS)
                .set(ANSWERS.BODY, answer.getBody())
                .set(ANSWERS.RIGHT, answer.isRight())
                .set(ANSWERS.QUESTION_ID, answer.getQuestionId())
                .execute();
    }

    @Override
    public int update(Answer answer) {
        return dslContext
                .update(ANSWERS)
                .set(ANSWERS.BODY, answer.getBody())
                .set(ANSWERS.RIGHT, answer.isRight())
                .where(ANSWERS.ID.eq(answer.getId()))
                .execute();
    }

    @Override
    public int delete(Long id) {
        return dslContext
                .deleteFrom(ANSWERS)
                .where(ANSWERS.ID.eq(id))
                .execute();
    }

    @Override
    public List<Answer> findByQuestionId(Long id) {
        return dslContext
                .selectFrom(ANSWERS)
                .where(ANSWERS.QUESTION_ID.eq(id))
                .fetchInto(Answer.class);
    }

    @Override
    public int saveAll(List<AnswerDto> answers) {
        return dslContext.batch(getInsertValues(answers)).execute().length;
    }

    private List<InsertValuesStep3<AnswersRecord, String, Boolean, Long>> getInsertValues(List<AnswerDto> answers) {
        return answers.stream()
                .map(answer -> dslContext
                        .insertInto(ANSWERS, ANSWERS.BODY, ANSWERS.RIGHT, ANSWERS.QUESTION_ID)
                        .values(answer.getBody(), answer.isRight(), answer.getQuestionId()))
                .collect(Collectors.toUnmodifiableList());
    }
}
