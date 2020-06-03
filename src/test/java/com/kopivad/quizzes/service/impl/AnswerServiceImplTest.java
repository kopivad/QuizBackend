package com.kopivad.quizzes.service.impl;

import com.kopivad.quizzes.domain.Answer;
import com.kopivad.quizzes.form.AnswerForm;
import com.kopivad.quizzes.repository.AnswerRepository;
import com.kopivad.quizzes.utils.AnswerUtils;
import com.kopivad.quizzes.utils.FormUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.List;

import static org.apache.commons.lang3.math.NumberUtils.INTEGER_ZERO;
import static org.apache.commons.lang3.math.NumberUtils.LONG_ONE;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertEquals;
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
        List<Answer> expectedResult = AnswerUtils.generateAnswers(size);
        when(answerRepository.findAll()).thenReturn(expectedResult);
        List<Answer> actualResult = answerService.getAll();
        assertEquals(expectedResult, actualResult);
    }

    @Test
    void testGetById() {
        Answer expectedResult = AnswerUtils.generateAnswer();
        when(answerRepository.findById(LONG_ONE)).thenReturn(expectedResult);
        Answer actualResult = answerService.getById(LONG_ONE);
        assertThat(actualResult, is(expectedResult));
        verify(answerRepository).findById(LONG_ONE);
    }

    @Test
    void testSave() {
        AnswerForm answerForSave = AnswerUtils.generateAnswerForm();
        Answer expectedResult = FormUtils.toAnswer(answerForSave);
        when(answerRepository.save(any())).thenReturn(expectedResult);
        Answer actualResult = answerService.save(answerForSave);
        assertThat(actualResult, is(expectedResult));
        verify(answerRepository).save(any());
    }

    @Test
    void testUpdate() {
        AnswerForm answerForUpdate = AnswerUtils.generateAnswerForm();
        String dataForUpdate = "Body";
        AnswerForm answerWithUpdatedData = answerForUpdate.toBuilder().body(dataForUpdate).build();
        Answer expectedResult = FormUtils.toAnswer(answerWithUpdatedData);
        when(answerRepository.update(anyLong(), any())).thenReturn(expectedResult);
        Answer actualResult = answerService.update(LONG_ONE, answerForUpdate);
        assertThat(actualResult, is(expectedResult));
        assertThat(actualResult.getBody(), is(expectedResult.getBody()));
        verify(answerRepository).update(eq(LONG_ONE), any());
    }

    @Test
    void testDelete() {
        answerService.delete(LONG_ONE);
        verify(answerRepository).delete(anyLong());
    }

    @Test
    void testSaveAll() {
        int size = 10;
        List<Answer> expectedResult = AnswerUtils.generateAnswers(size);
        when(answerRepository.save(any(Answer.class))).thenReturn(expectedResult.get(INTEGER_ZERO));
        List<Answer> actualResult = answerService.saveAll(expectedResult);
        assertThat(actualResult.size(), is(expectedResult.size()));
        verify(answerRepository, times(size)).save(any(Answer.class));
    }
}