package com.kopivad.quizzes.service.impl;

import com.kopivad.quizzes.domain.EvaluationStep;
import com.kopivad.quizzes.dto.EvaluationStepDto;
import com.kopivad.quizzes.mapper.EvaluationStepMapper;
import com.kopivad.quizzes.repository.EvaluationStepRepository;
import com.kopivad.quizzes.service.EvaluationStepService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EvaluationStepServiceImpl implements EvaluationStepService {
    private final EvaluationStepRepository evaluationStepRepository;
    private final EvaluationStepMapper stepMapper;

    @Override
    public long save(EvaluationStepDto step) {
        EvaluationStep evaluationStep = stepMapper.toEntity(step);
        return evaluationStepRepository.save(evaluationStep);
    }

    @Override
    public List<EvaluationStep> getByQuizId(Long id) {
        return evaluationStepRepository.findByQuizId(id);
    }

    @Override
    public void saveAll(List<EvaluationStepDto> dtos) {
        dtos.forEach(this::save);
    }
}
