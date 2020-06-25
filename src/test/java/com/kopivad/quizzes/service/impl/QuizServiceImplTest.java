package com.kopivad.quizzes.service.impl;

import com.kopivad.quizzes.domain.Quiz;
import com.kopivad.quizzes.form.QuizForm;
import com.kopivad.quizzes.repository.QuizRepository;
import com.kopivad.quizzes.service.QuestionService;
import com.kopivad.quizzes.utils.FormUtils;
import com.kopivad.quizzes.utils.QuizUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.List;

import static org.apache.commons.lang3.math.NumberUtils.LONG_ONE;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
class QuizServiceImplTest {
    @InjectMocks
    private QuizServiceImpl quizService;
    @Mock
    private QuizRepository quizRepository;
    @Mock
    private QuestionService questionService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void testGetAll() {
        int size = 10;
        List<Quiz> expectedResult = QuizUtils.generateQuizzes(size);
        when(quizRepository.findAll()).thenReturn(expectedResult);
        List<Quiz> actualResult = quizService.getAll();
        assertThat(actualResult, equalTo(expectedResult));
        verify(quizRepository).findAll();
    }

    @Test
    void testUpdateQuiz() {
        Quiz quizForUpdate = QuizUtils.generateQuiz();
        String dataForUpdate = "Title";
        Quiz expectedResult = quizForUpdate.toBuilder().title(dataForUpdate).build();
        when(quizRepository.update(eq(LONG_ONE), any())).thenReturn(expectedResult);
        Quiz actualResult = quizService.update(LONG_ONE, expectedResult);
        assertThat(actualResult, is(expectedResult));
        verify(quizRepository).update(eq(LONG_ONE), any());
    }

    @Test
    void testDeleteQuiz() {
        quizService.delete(LONG_ONE);
        verify(quizRepository).delete(anyLong());
    }

    @Test
    void testSaveQuiz() {
        Quiz expectedResult = QuizUtils.generateQuizWithQuestions();
        when(quizRepository.save(any())).thenReturn(expectedResult);
        when(questionService.saveAll(expectedResult.getQuestions())).thenReturn(expectedResult.getQuestions());
        Quiz actualResult = quizService.save(expectedResult);
        assertThat(actualResult, is(expectedResult));
        verify(quizRepository).save(any());
        verify(questionService).saveAll(expectedResult.getQuestions());
    }

    @Test
    void testSaveQuizWithNullQuestions() {
        Quiz expectedResult = QuizUtils.generateQuiz();
        when(quizRepository.save(any())).thenReturn(expectedResult);
        when(questionService.saveAll(expectedResult.getQuestions())).thenReturn(expectedResult.getQuestions());
        Quiz actualResult = quizService.save(expectedResult);
        assertThat(actualResult, is(expectedResult));
        verify(quizRepository).save(any());
        verify(questionService, never()).saveAll(any());
    }

    @Test
    void testGetQuizById() {
        Quiz dataForSave = QuizUtils.generateQuizWithQuestions();
        when(quizRepository.save(any())).thenReturn(dataForSave);
        when(quizRepository.findById(LONG_ONE)).thenReturn(dataForSave);
        when(questionService.getByQuizId(LONG_ONE)).thenReturn(dataForSave.getQuestions());
        when(questionService.saveAll(dataForSave.getQuestions())).thenReturn(dataForSave.getQuestions());

        Quiz expectedResult = quizService.save(dataForSave);
        Quiz actualResult = quizService.getById(expectedResult.getId());

        assertThat(actualResult, is(expectedResult));

        verify(quizRepository).save(any());
        verify(quizRepository).findById(LONG_ONE);
        verify(questionService).getByQuizId(LONG_ONE);
        verify(questionService).saveAll(dataForSave.getQuestions());
    }
}