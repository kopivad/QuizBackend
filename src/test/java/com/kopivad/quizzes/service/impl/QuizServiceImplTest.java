package com.kopivad.quizzes.service.impl;

import com.kopivad.quizzes.domain.EvaluationStep;
import com.kopivad.quizzes.domain.Quiz;
import com.kopivad.quizzes.dto.FullQuestionDto;
import com.kopivad.quizzes.dto.FullQuizDto;
import com.kopivad.quizzes.dto.QuizDto;
import com.kopivad.quizzes.repository.QuizRepository;
import com.kopivad.quizzes.service.EvaluationStepService;
import com.kopivad.quizzes.service.QuestionService;
import com.kopivad.quizzes.utils.EvaluationStepUtils;
import com.kopivad.quizzes.utils.QuestionUtils;
import com.kopivad.quizzes.utils.QuizUtils;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.apache.commons.lang3.math.NumberUtils.INTEGER_ONE;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.*;

class QuizServiceImplTest {
    private final QuizRepository quizRepository = mock(QuizRepository.class);
    private final QuestionService questionService = mock(QuestionService.class);
    private final EvaluationStepService evaluationStepService = mock(EvaluationStepService.class);
    private final QuizServiceImpl quizService = new QuizServiceImpl(quizRepository, questionService, evaluationStepService);

    @Test
    void getAllTest() {
        int size = 10;
        List<Quiz> expected = QuizUtils.generateQuizzes(size);
        when(quizRepository.findAll()).thenReturn(expected);
        List<Quiz> actual = quizService.getAll();

        assertThat(actual.size(), is(expected.size()));

        verify(quizRepository).findAll();
    }

    @Test
    void updateTest() {
        Quiz expected = QuizUtils.generateQuiz();
        when(quizRepository.update(any(Quiz.class))).thenReturn(INTEGER_ONE);
        boolean actual = quizService.update(expected);

        assertThat(actual, is(true));

        verify(quizRepository).update(any(Quiz.class));
    }

    @Test
    void deleteTest() {
        long expected = QuizUtils.generateQuiz().getId();
        when(quizRepository.delete(anyLong())).thenReturn(INTEGER_ONE);
        boolean actual = quizService.delete(expected);

        assertThat(actual, is(true));

        verify(quizRepository).delete(anyLong());
    }

    @Test
    void saveTest() {
        QuizDto expectedResult = QuizUtils.generateQuizDto();
        when(quizRepository.save(any(QuizDto.class))).thenReturn(QuizUtils.TEST_QUIZ_ID);
        when(evaluationStepService.saveAll(anyList())).thenReturn(true);
        when(questionService.saveAll(anyList())).thenReturn(true);

        boolean actual = quizService.save(expectedResult);

        assertThat(actual, is(true));

        verify(quizRepository).save(any(QuizDto.class));
        verify(evaluationStepService).saveAll(anyList());
        verify(questionService).saveAll(anyList());
    }

    @Test
    void getByIdTest() {
        Quiz expected = QuizUtils.generateQuiz();
        int size = 10;
        List<FullQuestionDto> questionDtos = QuestionUtils.generateFullQuestionDtos(size);
        List<EvaluationStep> evaluationSteps = EvaluationStepUtils.generateSteps(size);
        when(quizRepository.findById(anyLong())).thenReturn(Optional.of(expected));
        when(questionService.getFullByQuizId(anyLong())).thenReturn(questionDtos);
        when(evaluationStepService.getByQuizId(anyLong())).thenReturn(evaluationSteps);

        Optional<FullQuizDto> actual = quizService.getById(expected.getId());

        assertThat(actual.isPresent(), is(true));

        verify(quizRepository).findById(anyLong());
        verify(questionService).getFullByQuizId(anyLong());
        verify(evaluationStepService).getByQuizId(anyLong());
    }
}