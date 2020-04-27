package com.kopivad.quizzes.service.impl;

import com.kopivad.quizzes.domain.Answer;
import com.kopivad.quizzes.repository.AnswerRepository;
import com.kopivad.quizzes.repository.utils.AnswerUtils;
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
class AnswerServiceImplTest {
    @InjectMocks
    private AnswerServiceImpl answerService;
    
    @Mock
    private AnswerRepository answerRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void getAll() {
        List<Answer> answersFromDB = AnswerUtils.generateAnswers(10);
        when(answerRepository.findAll()).thenReturn(answersFromDB);
        List<Answer> answers = answerService.getAll();
        assertThat(answers, equalTo(answersFromDB));
    }

    @Test
    void getById() {
        Answer answerFromDB = AnswerUtils.generateAnswer();
        when(answerRepository.findById(1L)).thenReturn(answerFromDB);
        Answer answer = answerService.getById(1L);
        assertThat(answer, equalTo(answerFromDB));
        verify(answerRepository, times(1)).findById(1L);
    }

    @Test
    void save() {
        Answer answerForSave = AnswerUtils.generateAnswer();
        when(answerRepository.save(any())).thenReturn(answerForSave);
        Answer savedAnswer = answerService.save(answerForSave);
        assertThat(savedAnswer, equalTo(answerForSave));
        verify(answerRepository, times(1)).save(any());
    }

    @Test
    void update() {
        Answer answerForUpdate = AnswerUtils.generateAnswer();
        when(answerRepository.update(anyLong(), any())).thenReturn(answerForUpdate);
        Answer updatedAnswer = answerService.update(1L, answerForUpdate);
        assertThat(updatedAnswer.getId(), equalTo(answerForUpdate.getId()));
        assertThat(updatedAnswer, equalTo(answerForUpdate));
        verify(answerRepository, times(1)).update(anyLong(), any());
    }

    @Test
    void delete() {
        answerService.delete(1L);
        verify(answerRepository, times(1)).delete(anyLong());
    }
}