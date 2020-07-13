package com.kopivad.quizzes.service;

import com.kopivad.quizzes.domain.EvaluationStep;
import com.kopivad.quizzes.dto.EvaluationStepDto;

import java.util.List;

public interface EvaluationStepService {
    void saveAll(List<EvaluationStepDto> dtos);
    long save(EvaluationStepDto dto);

    List<EvaluationStep> getByQuizId(Long id);
}
