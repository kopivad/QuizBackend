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
        List<Quiz> quizzesFromDB = QuizUtils.generateQuizzes(10);
        when(quizRepository.findAll()).thenReturn(quizzesFromDB);
        List<Quiz> quizzes = quizService.getAll();
        assertThat(quizzes, equalTo(quizzesFromDB));
    }

    @Test
    void testSaveQuiz() {
        Quiz quizForSave = QuizUtils.generateQuiz();
        when(quizRepository.save(any())).thenReturn(quizForSave);
        Quiz savedQuiz = quizService.save(quizForSave);
        assertThat(savedQuiz.getCreationDate(), notNullValue());
        verify(quizRepository, times(1)).save(any());
    }

    @Test
    void testUpdateQuiz() {
        Quiz quizForUpdate = QuizUtils.generateQuiz();
        when(quizRepository.update(anyLong(), any())).thenReturn(quizForUpdate);
        Quiz updatedQuiz = quizService.update(1L, quizForUpdate);
        assertThat(updatedQuiz.getId(), equalTo(quizForUpdate.getId()));
        assertThat(updatedQuiz, equalTo(quizForUpdate));
        verify(quizRepository, times(1)).update(anyLong(), any());
    }

    @Test
    void testDeleteQuiz() {
        quizService.delete(1L);
        verify(quizRepository, times(1)).delete(anyLong());
    }

    @Test
    void testSaveQuizWithCorrectData() {
        Quiz actualResult = QuizUtils.generateFullQuiz();
        Quiz expectedResult = quizService.save(actualResult);
        assertThat(actualResult, not(expectedResult));
        assertThat(actualResult.getQuestions().size(), equalTo(expectedResult.getQuestions().size()));
        assertThat(actualResult.getAuthor(), equalTo(expectedResult.getAuthor()));
    }

    @Test
    void testSaveQuizWithNullQuestionsAndAnswers() {
        Quiz actualResult = QuizUtils.generateQuiz();
        Quiz expectedResult = quizService.save(actualResult);
        assertThat(actualResult, not(expectedResult));
        assertThat(actualResult.getTitle(), equalTo(expectedResult.getTitle()));
        assertThat(actualResult.getDescription(), equalTo(expectedResult.getDescription()));
    }

    @Test
    void testGetQuizById() {
        Quiz quizForSave = QuizUtils.generateFullQuiz();
        Quiz quizFromDB = quizForSave.toBuilder().id(LONG_ONE).build();
        when(quizRepository.save(any())).thenReturn(quizFromDB);
        when(quizRepository.findById(LONG_ONE)).thenReturn(quizFromDB);
        when(questionService.getByQuizId(LONG_ONE)).thenReturn(quizForSave.getQuestions());
        when(questionService.saveAll(quizForSave.getQuestions())).thenReturn(quizForSave.getQuestions());

        Quiz expectedResult = quizService.save(quizForSave);
        Quiz actualResult = quizService.getById(expectedResult.getId());

        assertThat(actualResult, notNullValue());
        assertThat(actualResult, is(expectedResult));
        assertEquals(actualResult.getQuestions(), expectedResult.getQuestions());

        verify(quizRepository, times(1)).save(any());
        verify(quizRepository, times(1)).findById(LONG_ONE);
        verify(questionService, times(1)).getByQuizId(LONG_ONE);
        verify(questionService, times(1)).saveAll(quizForSave.getQuestions());
    }
}