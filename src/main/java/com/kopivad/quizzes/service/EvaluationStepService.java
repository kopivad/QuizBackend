package com.kopivad.quizzes.service;

import com.kopivad.quizzes.domain.EvaluationStep;
import com.kopivad.quizzes.dto.EvaluationStepDto;

import java.util.List;

public interface EvaluationStepService {
    boolean saveAll(List<EvaluationStepDto> dtos);

    boolean save(EvaluationStepDto dto);

    List<EvaluationStep> getByQuizId(Long id);
}
