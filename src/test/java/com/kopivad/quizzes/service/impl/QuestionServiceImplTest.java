package com.kopivad.quizzes.service.impl;


import com.kopivad.quizzes.domain.Question;
import com.kopivad.quizzes.repository.QuestionRepository;
import com.kopivad.quizzes.repository.utils.QuestionUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.List;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
class QuestionServiceImplTest {
    @InjectMocks
    private QuestionServiceImpl questionService;
    
    @Mock
    private QuestionRepository questionRepository;
    
    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void getAll() {
        List<Question> questionsFromDB = QuestionUtils.generateQuestions(10);
        when(questionRepository.findAll()).thenReturn(questionsFromDB);
        List<Question> questions = questionService.getAll();
        assertThat(questions, equalTo(questionsFromDB));
    }

    @Test
    void getById() {
        Question questionFromDB = QuestionUtils.generateQuestion();
        when(questionRepository.findById(1L)).thenReturn(questionFromDB);
        Question question = questionService.getById(1L);
        assertThat(question, equalTo(questionFromDB));
        verify(questionRepository, times(1)).findById(1L);
    }

    @Test
    void save() {
        Question questionForSave = QuestionUtils.generateQuestion();
        when(questionRepository.save(any())).thenReturn(questionForSave);
        Question savedQuestion = questionService.save(questionForSave);
        assertThat(savedQuestion, equalTo(questionForSave));
        verify(questionRepository, times(1)).save(any());
    }

    @Test
    void update() {
        Question questionForUpdate = QuestionUtils.generateQuestion();
        when(questionRepository.update(anyLong(), any())).thenReturn(questionForUpdate);
        Question updatedQuestion = questionService.update(1L, questionForUpdate);
        assertThat(updatedQuestion.getId(), equalTo(questionForUpdate.getId()));
        assertThat(updatedQuestion, equalTo(questionForUpdate));
        verify(questionRepository, times(1)).update(anyLong(), any());
    }

    @Test
    void delete() {
        questionService.delete(1L);
        verify(questionRepository, times(1)).delete(anyLong());
    }
}