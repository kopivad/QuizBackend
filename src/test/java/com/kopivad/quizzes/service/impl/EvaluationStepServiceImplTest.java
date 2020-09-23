package com.kopivad.quizzes.service.impl;

import com.kopivad.quizzes.domain.EvaluationStep;
import com.kopivad.quizzes.dto.EvaluationStepDto;
import com.kopivad.quizzes.repository.EvaluationStepRepository;
import com.kopivad.quizzes.utils.EvaluationStepUtils;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.apache.commons.lang3.math.NumberUtils.INTEGER_ONE;
import static org.apache.commons.lang3.math.NumberUtils.LONG_ONE;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

class EvaluationStepServiceImplTest {
    private final EvaluationStepRepository evaluationStepRepository = mock(EvaluationStepRepository.class);
    private final EvaluationStepServiceImpl stepService = new EvaluationStepServiceImpl(evaluationStepRepository);

    @Test
    void saveTest() {
        EvaluationStepDto expected = EvaluationStepUtils.generateStepDto();
        when(evaluationStepRepository.save(any(EvaluationStepDto.class))).thenReturn(INTEGER_ONE);

        boolean actual = stepService.save(expected);

        assertThat(actual, is(true));

        verify(evaluationStepRepository).save(any(EvaluationStepDto.class));
    }

    @Test
    void getByQuizIdTest() {
        int size = 10;
        List<EvaluationStep> expected = EvaluationStepUtils.generateSteps(size);
        when(evaluationStepRepository.findByQuizId(anyLong())).thenReturn(expected);
        List<EvaluationStep> actual = stepService.getByQuizId(LONG_ONE);

        assertThat(actual.size(), is(expected.size()));

        verify(evaluationStepRepository).findByQuizId(anyLong());
    }

    @Test
    void saveAllTest() {
        int stepsCount = 10;
        List<EvaluationStepDto> expected = EvaluationStepUtils.generateStepDtos(stepsCount);
        when(evaluationStepRepository.saveAll(anyList())).thenReturn(stepsCount);
        boolean actual = stepService.saveAll(expected);

        assertThat(actual, is(true));

        verify(evaluationStepRepository).saveAll(anyList());
    }
}