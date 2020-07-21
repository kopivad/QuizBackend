package com.kopivad.quizzes.service.impl;

import com.kopivad.quizzes.domain.EvaluationStep;
import com.kopivad.quizzes.dto.EvaluationStepDto;
import com.kopivad.quizzes.mapper.EvaluationStepMapper;
import com.kopivad.quizzes.repository.EvaluationStepRepository;
import com.kopivad.quizzes.utils.EvaluationStepUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.List;

import static org.apache.commons.lang3.math.NumberUtils.LONG_ONE;
import static org.mockito.ArgumentMatchers.any;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
class EvaluationStepServiceImplTest {
    @InjectMocks
    private EvaluationStepServiceImpl stepService;
    @Mock
    private EvaluationStepRepository stepRepository;
    @Mock
    private EvaluationStepMapper stepMapper;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void testSave() {
        EvaluationStepDto expected = EvaluationStepUtils.generateStepDto();
        when(stepRepository.save(any(EvaluationStep.class))).thenReturn(expected.getId());
        when(stepMapper.toEntity(any(EvaluationStepDto.class))).thenReturn(EvaluationStepUtils.generateStep());
        long actual = stepService.save(expected);

        assertThat(actual, is(expected.getId()));

        verify(stepRepository).save(any(EvaluationStep.class));
        verify(stepMapper).toEntity(any(EvaluationStepDto.class));
    }

    @Test
    void testGetByQuizId() {
        int size = 10;
        List<EvaluationStep> expected = EvaluationStepUtils.generateSteps(size);
        when(stepRepository.findByQuizId(anyLong())).thenReturn(expected);
        List<EvaluationStep> actual = stepService.getByQuizId(LONG_ONE);

        assertThat(actual.size(), is(expected.size()));

        verify(stepRepository).findByQuizId(anyLong());
    }

    @Test
    void testSaveAll() {
        int stepsCount = 10;
        List<EvaluationStepDto> expected = EvaluationStepUtils.generateStepDtos(stepsCount);
        when(stepRepository.save(any(EvaluationStep.class))).thenReturn(LONG_ONE);
        when(stepMapper.toEntity(any(EvaluationStepDto.class))).thenReturn(EvaluationStepUtils.generateStep());
        stepService.saveAll(expected);

        assertThat(stepsCount, is(expected.size()));

        verify(stepRepository, times(stepsCount)).save(any(EvaluationStep.class));
        verify(stepMapper, times(stepsCount)).toEntity(any(EvaluationStepDto.class));
    }
}