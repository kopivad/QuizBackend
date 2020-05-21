package com.kopivad.quizzes.service.impl;

import com.kopivad.quizzes.domain.Answer;
import com.kopivad.quizzes.repository.AnswerRepository;
import com.kopivad.quizzes.utils.AnswerUtils;
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
    void testGetAll() {
        int size = 10;
        List<Answer> answersFromDB = AnswerUtils.generateAnswers(size);
        when(answerRepository.findAll()).thenReturn(answersFromDB);
        List<Answer> answers = answerService.getAll();
        assertThat(answers, equalTo(answersFromDB));
    }

    @Test
    void testGetById() {
        Answer answerFromDB = AnswerUtils.generateAnswer();
        when(answerRepository.findById(LONG_ONE)).thenReturn(answerFromDB);
        Answer answer = answerService.getById(LONG_ONE);
        assertThat(answer, equalTo(answerFromDB));
        verify(answerRepository).findById(LONG_ONE);
    }

    @Test
    void testSave() {
        Answer answerForSave = AnswerUtils.generateAnswer();
        when(answerRepository.save(any())).thenReturn(answerForSave);
        Answer savedAnswer = answerService.save(answerForSave);
        assertThat(savedAnswer, equalTo(answerForSave));
        verify(answerRepository).save(any());
    }

    @Test
    void testUpdate() {
        Answer answerForUpdate = AnswerUtils.generateAnswer();
        when(answerRepository.update(anyLong(), any())).thenReturn(answerForUpdate);
        Answer updatedAnswer = answerService.update(LONG_ONE, answerForUpdate);
        assertThat(updatedAnswer.getId(), equalTo(answerForUpdate.getId()));
        assertThat(updatedAnswer, equalTo(answerForUpdate));
        verify(answerRepository).update(LONG_ONE, any());
    }

    @Test
    void testDelete() {
        answerService.delete(LONG_ONE);
        verify(answerRepository).delete(anyLong());
    }
}