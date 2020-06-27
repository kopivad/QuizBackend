package com.kopivad.quizzes.service.impl;

import com.kopivad.quizzes.domain.EvaluationStep;
import com.kopivad.quizzes.repository.EvaluationStepRepository;
import com.kopivad.quizzes.service.EvaluationStepService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class EvaluationStepServiceImpl implements EvaluationStepService {
    private final EvaluationStepRepository evaluationStepRepository;

    @Override
    public List<EvaluationStep> saveAll(List<EvaluationStep> steps) {
        return steps.stream()
                .map(this::save)
                .collect(Collectors.toUnmodifiableList());
    }

    @Override
    public EvaluationStep save(EvaluationStep step) {
        return evaluationStepRepository.save(step);
    }
}
