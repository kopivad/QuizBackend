package com.kopivad.quizzes.repository.jooq;

import com.kopivad.quizzes.domain.EvaluationStep;
import com.kopivad.quizzes.domain.db.tables.records.StepsRecord;
import com.kopivad.quizzes.dto.EvaluationStepDto;
import com.kopivad.quizzes.repository.EvaluationStepRepository;
import lombok.RequiredArgsConstructor;
import org.jooq.DSLContext;
import org.jooq.InsertValuesStep4;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.stream.Collectors;

import static com.kopivad.quizzes.domain.db.tables.Steps.STEPS;

@Repository
@RequiredArgsConstructor
public class EvaluationStepRepositoryImpl implements EvaluationStepRepository {
    private final DSLContext dslContext;

    @Override
    public int save(EvaluationStepDto step) {
        return dslContext
                .insertInto(STEPS)
                .set(STEPS.MIN_TOTAL, step.getMinTotal())
                .set(STEPS.MAX_TOTAL, step.getMaxTotal())
                .set(STEPS.RATING, step.getRating())
                .set(STEPS.QUIZ_ID, step.getQuizId())
                .execute();
    }

    @Override
    public List<EvaluationStep> findByQuizId(Long id) {
        return dslContext
                .selectFrom(STEPS)
                .where(STEPS.QUIZ_ID.eq(id))
                .fetchInto(EvaluationStep.class);
    }

    @Override
    public int saveAll(List<EvaluationStepDto> dtos) {
        return dslContext.batch(getInsertValues(dtos)).execute().length;
    }

    private List<InsertValuesStep4<StepsRecord, Integer, Integer, String, Long>> getInsertValues(List<EvaluationStepDto> dtos) {
        return dtos.stream()
                .map(dto -> dslContext
                        .insertInto(STEPS, STEPS.MIN_TOTAL, STEPS.MAX_TOTAL, STEPS.RATING, STEPS.QUIZ_ID)
                        .values(dto.getMinTotal(), dto.getMaxTotal(), dto.getRating(), dto.getQuizId()))
                .collect(Collectors.toUnmodifiableList());
    }
}
