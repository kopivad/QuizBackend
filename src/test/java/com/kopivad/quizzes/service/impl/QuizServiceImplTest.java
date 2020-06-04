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
    }

    @Test
    void testUpdateQuiz() {
        String dataForUpdate = "Title";
        QuizForm quizForUpdate = QuizUtils.generateQuizForm();
        Quiz expectedResult = FormUtils
                .toQuiz(quizForUpdate)
                .toBuilder()
                .title(dataForUpdate)
                .build();
        when(quizRepository.update(eq(LONG_ONE), any())).thenReturn(expectedResult);
        Quiz actualResult = quizService.update(LONG_ONE, quizForUpdate);
        assertThat(actualResult, is(expectedResult));
        assertThat(actualResult.getTitle(), not(quizForUpdate.getTitle()));
        verify(quizRepository).update(eq(LONG_ONE), any());
    }

    @Test
    void testDeleteQuiz() {
        quizService.delete(LONG_ONE);
        verify(quizRepository).delete(anyLong());
    }

    @Test
    void testSaveQuiz() {
        QuizForm quizForSave = QuizUtils.generateQuizFormWithQuestions();
        Quiz expectedResult = FormUtils
                .toQuiz(quizForSave)
                .toBuilder()
                .id(LONG_ONE)
                .build();
        when(quizRepository.save(any())).thenReturn(expectedResult);
        when(questionService.saveAll(expectedResult.getQuestions())).thenReturn(expectedResult.getQuestions());
        Quiz actualResult = quizService.save(quizForSave);
        assertThat(actualResult, is(expectedResult));
        assertEquals(actualResult.getQuestions(), expectedResult.getQuestions());
        assertThat(actualResult.getAuthor(), equalTo(expectedResult.getAuthor()));
        verify(quizRepository).save(any());
        verify(questionService).saveAll(expectedResult.getQuestions());
    }

    @Test
    void testSaveQuizWithNullQuestions() {
        QuizForm quizForSave = QuizUtils.generateQuizForm();
        Quiz expectedResult = FormUtils
                .toQuiz(quizForSave)
                .toBuilder()
                .id(LONG_ONE)
                .build();
        when(quizRepository.save(any())).thenReturn(expectedResult);
        when(questionService.saveAll(expectedResult.getQuestions())).thenReturn(expectedResult.getQuestions());
        Quiz actualResult = quizService.save(quizForSave);
        assertThat(actualResult, is(expectedResult));
        assertEquals(actualResult.getQuestions(), expectedResult.getQuestions());
        assertThat(actualResult.getAuthor(), equalTo(expectedResult.getAuthor()));
        verify(quizRepository).save(any());
        verify(questionService, never()).saveAll(any());
    }

    @Test
    void testGetQuizById() {
        QuizForm quizForSave = QuizUtils.generateQuizFormWithQuestions();
        Quiz savedQuiz = FormUtils
                .toQuiz(quizForSave)
                .toBuilder()
                .id(LONG_ONE)
                .build();
        when(quizRepository.save(any())).thenReturn(savedQuiz);
        when(quizRepository.findById(LONG_ONE)).thenReturn(savedQuiz);
        when(questionService.getByQuizId(LONG_ONE)).thenReturn(savedQuiz.getQuestions());
        when(questionService.saveAll(savedQuiz.getQuestions())).thenReturn(savedQuiz.getQuestions());

        Quiz expectedResult = quizService.save(quizForSave);
        Quiz actualResult = quizService.getById(expectedResult.getId());

        assertThat(actualResult, notNullValue());
        assertThat(actualResult, is(expectedResult));
        assertEquals(actualResult.getQuestions(), expectedResult.getQuestions());

        verify(quizRepository).save(any());
        verify(quizRepository).findById(LONG_ONE);
        verify(questionService).getByQuizId(LONG_ONE);
        verify(questionService).saveAll(expectedResult.getQuestions());
    }
}