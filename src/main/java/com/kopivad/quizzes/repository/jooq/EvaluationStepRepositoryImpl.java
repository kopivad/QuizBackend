package com.kopivad.quizzes.repository.jooq;

import com.kopivad.quizzes.domain.EvaluationStep;
import com.kopivad.quizzes.domain.Quiz;
import com.kopivad.quizzes.repository.EvaluationStepRepository;
import lombok.RequiredArgsConstructor;
import org.jooq.DSLContext;
import org.jooq.Record;
import org.jooq.RecordMapper;
import org.springframework.stereotype.Repository;

import static com.kopivad.quizzes.domain.db.tables.EvaluationSteps.EVALUATION_STEPS;

@Repository
@RequiredArgsConstructor
public class EvaluationStepRepositoryImpl implements EvaluationStepRepository {
    private final DSLContext dslContext;

    @Override
    public EvaluationStep save(EvaluationStep step) {
        return dslContext
                .insertInto(EVALUATION_STEPS)
                .set(EVALUATION_STEPS.MINTOTAL, step.getMinTotal())
                .set(EVALUATION_STEPS.RATING, step.getRating())
                .set(EVALUATION_STEPS.QUIZ_ID, step.getQuiz().getId())
                .returning(EVALUATION_STEPS.fields())
                .fetchOne()
                .map(getEvaluationStepRecordMapper());
    }

    private RecordMapper<Record, EvaluationStep> getEvaluationStepRecordMapper() {
        return record -> EvaluationStep
                .builder()
                .id(record.getValue(EVALUATION_STEPS.ID))
                .minTotal(record.getValue(EVALUATION_STEPS.MINTOTAL))
                .rating(record.getValue(EVALUATION_STEPS.RATING))
                .quiz(Quiz.builder().id(record.getValue(EVALUATION_STEPS.QUIZ_ID)).build())
                .build();
    }
}
