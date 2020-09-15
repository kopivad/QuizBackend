package com.kopivad.quizzes.service.impl;

import com.kopivad.quizzes.domain.EvaluationStep;
import com.kopivad.quizzes.dto.SaveEvaluationStepDto;
import com.kopivad.quizzes.repository.EvaluationStepRepository;
import com.kopivad.quizzes.service.EvaluationStepService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EvaluationStepServiceImpl implements EvaluationStepService {
    private final EvaluationStepRepository evaluationStepRepository;

    @Override
    public boolean save(SaveEvaluationStepDto dto) {
        EvaluationStep evaluationStep = new EvaluationStep(1L, dto.getMinTotal(), dto.getMaxTotal(), dto.getRating(), dto.getQuizId());
        return evaluationStepRepository.save(evaluationStep);
    }

    @Override
    public List<EvaluationStep> getByQuizId(Long id) {
        return evaluationStepRepository.findByQuizId(id);
    }

    @Override
    public boolean saveAll(List<SaveEvaluationStepDto> dtos) {
        dtos.forEach(this::save);
        return true; // Looking for better solution, will better to check if all was saved
    }
}
