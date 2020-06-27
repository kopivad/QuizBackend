package com.kopivad.quizzes.service;

import com.kopivad.quizzes.domain.EvaluationStep;

import java.util.List;

public interface EvaluationStepService {
    List<EvaluationStep> saveAll(List<EvaluationStep> steps);
    EvaluationStep save(EvaluationStep step);
}
