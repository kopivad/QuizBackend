package com.kopivad.quizzes.service;

import com.kopivad.quizzes.domain.EvaluationStep;
import com.kopivad.quizzes.dto.SaveEvaluationStepDto;

import java.util.List;

public interface EvaluationStepService {
    boolean saveAll(List<SaveEvaluationStepDto> dtos);

    boolean save(SaveEvaluationStepDto dto);

    List<EvaluationStep> getByQuizId(Long id);
}
