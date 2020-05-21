package com.kopivad.quizzes.service.impl;


import com.kopivad.quizzes.domain.Question;
import com.kopivad.quizzes.repository.QuestionRepository;
import com.kopivad.quizzes.utils.QuestionUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.List;

import static org.apache.commons.lang3.math.NumberUtils.LONG_ONE;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

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
    void testGetAll() {
        int size = 10;
        List<Question> questionsFromDB = QuestionUtils.generateQuestions(size);
        when(questionRepository.findAll()).thenReturn(questionsFromDB);
        List<Question> questions = questionService.getAll();
        assertThat(questions, equalTo(questionsFromDB));
    }

    @Test
    void testGetById() {
        Question questionFromDB = QuestionUtils.generateQuestion();
        when(questionRepository.findById(LONG_ONE)).thenReturn(questionFromDB);
        Question question = questionService.getById(LONG_ONE);
        assertThat(question, equalTo(questionFromDB));
        verify(questionRepository).findById(LONG_ONE);
    }

    @Test
    void testSave() {
        Question questionForSave = QuestionUtils.generateQuestion();
        when(questionRepository.save(any())).thenReturn(questionForSave);
        Question savedQuestion = questionService.save(questionForSave);
        assertThat(savedQuestion, equalTo(questionForSave));
        verify(questionRepository).save(any());
    }

    @Test
    void testUpdate() {
        Question questionForUpdate = QuestionUtils.generateQuestion();
        when(questionRepository.update(LONG_ONE, any())).thenReturn(questionForUpdate);
        Question updatedQuestion = questionService.update(LONG_ONE, questionForUpdate);
        assertThat(updatedQuestion.getId(), equalTo(questionForUpdate.getId()));
        assertThat(updatedQuestion, equalTo(questionForUpdate));
        verify(questionRepository).update(LONG_ONE, any());
    }

    @Test
    void testDelete() {
        questionService.delete(LONG_ONE);
        verify(questionRepository).delete(LONG_ONE);
    }
}