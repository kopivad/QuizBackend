package com.kopivad.quizzes.repository.jooq;

import com.kopivad.quizzes.domain.EvaluationStep;
import com.kopivad.quizzes.repository.EvaluationStepRepository;
import lombok.RequiredArgsConstructor;
import org.jooq.DSLContext;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.kopivad.quizzes.domain.db.tables.Steps.STEPS;
import static org.apache.commons.lang3.math.NumberUtils.INTEGER_ZERO;

@Repository
@RequiredArgsConstructor
public class EvaluationStepRepositoryImpl implements EvaluationStepRepository {
    private final DSLContext dslContext;

    @Override
    public boolean save(EvaluationStep step) {
        int affectedRows = dslContext
                .insertInto(STEPS)
                .set(STEPS.MIN_TOTAL, step.getMinTotal())
                .set(STEPS.MAX_TOTAL, step.getMaxTotal())
                .set(STEPS.RATING, step.getRating())
                .set(STEPS.QUIZ_ID, step.getQuizId())
                .execute();

        return affectedRows > INTEGER_ZERO;
    }

    @Override
    public List<EvaluationStep> findByQuizId(Long id) {
        return dslContext
                .selectFrom(STEPS)
                .where(STEPS.QUIZ_ID.eq(id))
                .fetchInto(EvaluationStep.class);
    }
}
