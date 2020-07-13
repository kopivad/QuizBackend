package com.kopivad.quizzes.repository.jooq;

import com.kopivad.quizzes.domain.EvaluationStep;
import com.kopivad.quizzes.domain.Quiz;
import com.kopivad.quizzes.domain.db.tables.records.EvaluationStepsRecord;
import com.kopivad.quizzes.repository.EvaluationStepRepository;
import lombok.RequiredArgsConstructor;
import org.jooq.DSLContext;
import org.jooq.Record;
import org.jooq.RecordMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.kopivad.quizzes.domain.db.tables.EvaluationSteps.EVALUATION_STEPS;

@Repository
@RequiredArgsConstructor
public class EvaluationStepRepositoryImpl implements EvaluationStepRepository {
    private final DSLContext dslContext;

    @Override
    public long save(EvaluationStep step) {
        return dslContext
                .insertInto(EVALUATION_STEPS)
                .set(EVALUATION_STEPS.MINTOTAL, step.getMinTotal())
                .set(EVALUATION_STEPS.MAXTOTAL, step.getMaxTotal())
                .set(EVALUATION_STEPS.RATING, step.getRating())
                .set(EVALUATION_STEPS.QUIZ_ID, step.getQuiz().getId())
                .returning(EVALUATION_STEPS.ID)
                .fetchOne()
                .getId();
    }

    @Override
    public List<EvaluationStep> findByQuizId(Long id) {
        return dslContext
                .selectFrom(EVALUATION_STEPS)
                .where(EVALUATION_STEPS.QUIZ_ID.eq(id))
                .fetch()
                .map(getEvaluationStepsRecordEvaluationStepRecordMapper());
    }

    public RecordMapper<Record, EvaluationStep> getEvaluationStepsRecordEvaluationStepRecordMapper() {
        return r -> EvaluationStep
                .builder()
                .quiz(Quiz.builder().id(r.getValue(EVALUATION_STEPS.QUIZ_ID)).build())
                .rating(r.getValue(EVALUATION_STEPS.RATING))
                .id(r.getValue(EVALUATION_STEPS.ID))
                .minTotal(r.getValue(EVALUATION_STEPS.MINTOTAL))
                .maxTotal(r.getValue(EVALUATION_STEPS.MAXTOTAL))
                .build();
    }
}
