package com.kopivad.quizzes.service.impl;

import com.kopivad.quizzes.domain.Quiz;
import com.kopivad.quizzes.repository.QuizRepository;
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

import static org.apache.commons.lang3.math.NumberUtils.LONG_ONE;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.any;
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
        List<Quiz> quizzesFromDB = QuizUtils.generateQuizzes(size);
        when(quizRepository.findAll()).thenReturn(quizzesFromDB);
        List<Quiz> quizzes = quizService.getAll();
        assertThat(quizzes, equalTo(quizzesFromDB));
    }

    @Test
    void testUpdateQuiz() {
        Quiz quizForUpdate = QuizUtils.generateQuiz();
        when(quizRepository.update(anyLong(), any())).thenReturn(quizForUpdate);
        Quiz updatedQuiz = quizService.update(LONG_ONE, quizForUpdate);
        assertThat(updatedQuiz.getId(), equalTo(quizForUpdate.getId()));
        assertThat(updatedQuiz, equalTo(quizForUpdate));
        verify(quizRepository, times(1)).update(anyLong(), any());
    }

    @Test
    void testDeleteQuiz() {
        quizService.delete(LONG_ONE);
        verify(quizRepository, times(1)).delete(anyLong());
    }

    @Test
    void testSaveQuiz() {
        Quiz expectedResult = QuizUtils.generateFullQuiz();
        when(quizRepository.save(any())).thenReturn(expectedResult);
        when(questionService.saveAll(expectedResult.getQuestions())).thenReturn(expectedResult.getQuestions());
        Quiz actualResult = quizService.save(expectedResult);
        assertThat(actualResult, is(expectedResult));
        assertEquals(actualResult.getQuestions(), expectedResult.getQuestions());
        assertThat(actualResult.getAuthor(), equalTo(expectedResult.getAuthor()));
        verify(quizRepository, times(1)).save(any());
        verify(questionService, times(1)).saveAll(expectedResult.getQuestions());
    }

    @Test
    void testSaveQuizWithNullQuestionsAndAnswers() {
        Quiz expectedResult = QuizUtils.generateQuiz();
        when(quizRepository.save(any())).thenReturn(expectedResult);
        when(questionService.saveAll(expectedResult.getQuestions())).thenReturn(expectedResult.getQuestions());
        Quiz actualResult = quizService.save(expectedResult);
        assertThat(actualResult, is(expectedResult));
        assertEquals(actualResult.getQuestions(), expectedResult.getQuestions());
        assertThat(actualResult.getAuthor(), equalTo(expectedResult.getAuthor()));
        verify(quizRepository, times(1)).save(any());
        verify(questionService, times(0)).saveAll(any());
    }

    @Test
    void testGetQuizById() {
        Quiz dataForSave = QuizUtils.generateFullQuiz();
        when(quizRepository.save(any())).thenReturn(dataForSave);
        when(quizRepository.findById(LONG_ONE)).thenReturn(dataForSave);
        when(questionService.getByQuizId(LONG_ONE)).thenReturn(dataForSave.getQuestions());
        when(questionService.saveAll(dataForSave.getQuestions())).thenReturn(dataForSave.getQuestions());

        Quiz expectedResult = quizService.save(dataForSave);
        Quiz actualResult = quizService.getById(expectedResult.getId());

        assertThat(actualResult, notNullValue());
        assertThat(actualResult, is(expectedResult));
        assertEquals(actualResult.getQuestions(), expectedResult.getQuestions());

        verify(quizRepository, times(1)).save(any());
        verify(quizRepository, times(1)).findById(LONG_ONE);
        verify(questionService, times(1)).getByQuizId(LONG_ONE);
        verify(questionService, times(1)).saveAll(dataForSave.getQuestions());
    }
}