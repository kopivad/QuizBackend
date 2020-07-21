package com.kopivad.quizzes.service.impl;

import com.kopivad.quizzes.domain.Quiz;
import com.kopivad.quizzes.dto.QuizDto;
import com.kopivad.quizzes.mapper.QuizMapper;
import com.kopivad.quizzes.repository.QuizRepository;
import com.kopivad.quizzes.service.EvaluationStepService;
import com.kopivad.quizzes.service.QuestionService;
import com.kopivad.quizzes.utils.QuizUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
class QuizServiceImplTest {
    @InjectMocks
    private QuizServiceImpl quizService;
    @Mock
    private QuizRepository quizRepository;
    @Mock
    private QuestionService questionService;
    @Mock
    private EvaluationStepService evaluationStepService;
    @Mock
    private QuizMapper quizMapper;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void testGetAll() {
        int size = 10;
        List<Quiz> expected = QuizUtils.generateQuizzes(size);
        when(quizRepository.findAll()).thenReturn(expected);
        when(quizMapper.toDto(any(Quiz.class))).thenReturn(QuizUtils.generateQuizDto());
        List<QuizDto> actual = quizService.getAll();

        assertThat(actual.size(), is(expected.size()));

        verify(quizRepository).findAll();
        verify(quizMapper, times(size)).toDto(any(Quiz.class));
    }

    @Test
    void testUpdate() {
        QuizDto expected = QuizUtils.generateQuizDto();
        when(quizMapper.toEntity(any(QuizDto.class))).thenReturn(QuizUtils.generateQuiz());
        when(quizRepository.update(any(Quiz.class))).thenReturn(true);
        boolean actual = quizService.update(expected);

        assertTrue(actual);

        verify(quizRepository).update(any(Quiz.class));
        verify(quizMapper).toEntity(any(QuizDto.class));
    }

    @Test
    void testDelete() {
        long expected = QuizUtils.generateQuiz().getId();
        when(quizRepository.delete(anyLong())).thenReturn(true);
        boolean actual = quizService.delete(expected);

        assertTrue(actual);

        verify(quizRepository).delete(anyLong());
    }

    @Test
    void testSaveQuizDtoWithFullData() {
        QuizDto expectedResult = QuizUtils.generateQuizDtoWithFullData();
        when(quizMapper.toEntity(any(QuizDto.class))).thenReturn(QuizUtils.generateQuizWithFullData());
        when(quizRepository.save(any(Quiz.class))).thenReturn(expectedResult.getId());
        long actualResult = quizService.save(expectedResult);

        assertThat(actualResult, is(expectedResult.getId()));

        verify(quizRepository).save(any(Quiz.class));
        verify(quizMapper).toEntity(any(QuizDto.class));
        verify(questionService).saveAll(anyList());
        verify(evaluationStepService).saveAll(anyList());
    }

    @Test
    void testSaveQuizDtoWithNullData() {
        QuizDto expectedResult = QuizUtils.generateQuizDto();
        when(quizMapper.toEntity(any(QuizDto.class))).thenReturn(QuizUtils.generateQuiz());
        when(quizRepository.save(any())).thenReturn(expectedResult.getId());
        long actualResult = quizService.save(expectedResult);

        assertThat(actualResult, is(expectedResult.getId()));

        verify(quizRepository).save(any(Quiz.class));
        verify(quizMapper).toEntity(any(QuizDto.class));
        verify(evaluationStepService, never()).saveAll(anyList());
        verify(questionService, never()).saveAll(anyList());
    }

    @Test
    void testGetQuizById() {
        Quiz expected = QuizUtils.generateQuizWithFullData();
        when(quizRepository.findById(anyLong())).thenReturn(expected);
        when(questionService.getByQuizId(anyLong())).thenReturn(expected.getQuestions());
        when(evaluationStepService.getByQuizId(anyLong())).thenReturn(expected.getEvaluationSteps());
        Quiz actual = quizService.getById(expected.getId());

        assertThat(actual, is(expected));

        verify(quizRepository).findById(anyLong());
        verify(questionService).getByQuizId(anyLong());
        verify(evaluationStepService).getByQuizId(anyLong());
    }
}