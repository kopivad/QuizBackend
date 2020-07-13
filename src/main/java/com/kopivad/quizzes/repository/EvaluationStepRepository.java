package com.kopivad.quizzes.repository;

import com.kopivad.quizzes.domain.EvaluationStep;

import java.util.List;

public interface EvaluationStepRepository {
    long save(EvaluationStep step);

    List<EvaluationStep> findByQuizId(Long id);
}
