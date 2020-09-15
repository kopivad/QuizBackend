package com.kopivad.quizzes.service.impl;

import com.kopivad.quizzes.domain.EvaluationStep;
import com.kopivad.quizzes.dto.EvaluationStepDto;
import com.kopivad.quizzes.repository.EvaluationStepRepository;
import com.kopivad.quizzes.service.EvaluationStepService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

import static org.apache.commons.lang3.math.NumberUtils.INTEGER_ZERO;

@Service
@RequiredArgsConstructor
public class EvaluationStepServiceImpl implements EvaluationStepService {
    private final EvaluationStepRepository evaluationStepRepository;

    @Override
    public boolean save(EvaluationStepDto dto) {
        int affectedRows = evaluationStepRepository.save(dto);
        return affectedRows == INTEGER_ZERO;
    }

    @Override
    public List<EvaluationStep> getByQuizId(Long id) {
        return evaluationStepRepository.findByQuizId(id);
    }

    @Override
    public boolean saveAll(List<EvaluationStepDto> dtos) {
        int affectedRows = evaluationStepRepository.saveAll(dtos);
        return affectedRows == dtos.size();
    }
}
