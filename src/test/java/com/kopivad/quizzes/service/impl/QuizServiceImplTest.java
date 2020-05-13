package com.kopivad.quizzes.service.impl;

import com.kopivad.quizzes.domain.Quiz;
import com.kopivad.quizzes.repository.QuizRepository;
import com.kopivad.quizzes.utils.QuizUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.List;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
class QuizServiceImplTest {
    @InjectMocks
    private QuizServiceImpl quizService;
    @Mock
    private QuizRepository quizRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void getAll() {
        List<Quiz> quizzesFromDB = QuizUtils.generateQuizzes(10);
        when(quizRepository.findAll()).thenReturn(quizzesFromDB);
        List<Quiz> quizzes = quizService.getAll();
        assertThat(quizzes, equalTo(quizzesFromDB));
    }

    @Test
    void getById() {
        Quiz quizFromDB = QuizUtils.generateQuiz();
        when(quizRepository.findById(1L)).thenReturn(quizFromDB);
        Quiz quiz = quizService.getById(1L);
        assertThat(quiz, equalTo(quizFromDB));
        verify(quizRepository, times(1)).findById(1L);
    }

    @Test
    void save() {
        Quiz quizForSave = QuizUtils.generateQuiz();
        when(quizRepository.save(any())).thenReturn(quizForSave);
        Quiz savedQuiz = quizService.save(quizForSave);
        assertThat(savedQuiz.getCreationDate(), notNullValue());
        verify(quizRepository, times(1)).save(any());
    }

    @Test
    void update() {
        Quiz quizForUpdate = QuizUtils.generateQuiz();
        when(quizRepository.update(anyLong(), any())).thenReturn(quizForUpdate);
        Quiz updatedQuiz = quizService.update(1L, quizForUpdate);
        assertThat(updatedQuiz.getId(), equalTo(quizForUpdate.getId()));
        assertThat(updatedQuiz, equalTo(quizForUpdate));
        verify(quizRepository, times(1)).update(anyLong(), any());
    }

    @Test
    void delete() {
        quizService.delete(1L);
        verify(quizRepository, times(1)).delete(anyLong());
    }
}