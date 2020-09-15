package com.kopivad.quizzes.repository;

import com.kopivad.quizzes.domain.EvaluationStep;
import com.kopivad.quizzes.dto.EvaluationStepDto;

import java.util.List;

public interface EvaluationStepRepository {
    int save(EvaluationStepDto step);

    List<EvaluationStep> findByQuizId(Long id);

    int saveAll(List<EvaluationStepDto> dtos);
}
