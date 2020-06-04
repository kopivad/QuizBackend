package com.kopivad.quizzes.service.impl;


import com.kopivad.quizzes.domain.Question;
import com.kopivad.quizzes.form.QuestionForm;
import com.kopivad.quizzes.repository.QuestionRepository;
import com.kopivad.quizzes.service.AnswerService;
import com.kopivad.quizzes.utils.FormUtils;
import com.kopivad.quizzes.utils.QuestionUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.List;

import static org.apache.commons.lang3.math.NumberUtils.*;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
class QuestionServiceImplTest {
    @InjectMocks
    private QuestionServiceImpl questionService;
    
    @Mock
    private QuestionRepository questionRepository;

    @Mock
    private AnswerService answerService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void testGetAll() {
        int size = 10;
        List<Question> expectedResult = QuestionUtils.generateQuestions(size);
        when(questionRepository.findAll()).thenReturn(expectedResult);
        List<Question> actualResult = questionService.getAll();
        assertEquals(expectedResult, actualResult);
    }

    @Test
    void testSaveQuestion() {
        QuestionForm questionForSave = QuestionUtils.generateQuestionFormWithAnswers();
        Question expectedResult = FormUtils.toQuestion(questionForSave)
                .toBuilder()
                .id(LONG_ONE)
                .build();
        when(questionRepository.save(FormUtils.toQuestion(questionForSave))).thenReturn(expectedResult);
        when(answerService.saveAll(expectedResult.getAnswers())).thenReturn(expectedResult.getAnswers());
        Question actualResult = questionService.save(questionForSave);
        assertThat(actualResult, is(expectedResult));
        assertEquals(actualResult.getAnswers(), expectedResult.getAnswers());
        assertThat(actualResult.getQuiz(), is(expectedResult.getQuiz()));
        verify(questionRepository).save(any());
        verify(answerService).saveAll(expectedResult.getAnswers());
    }

    @Test
    void testSaveQuestionWithNullAnswers() {
        QuestionForm questionForSave = QuestionUtils.generateQuestionForm();
        Question expectedResult = FormUtils.toQuestion(questionForSave)
                .toBuilder()
                .id(LONG_ONE)
                .build();
        when(questionRepository.save(any())).thenReturn(expectedResult);
        when(answerService.saveAll(expectedResult.getAnswers())).thenReturn(expectedResult.getAnswers());
        Question actualResult = questionService.save(questionForSave);
        assertThat(actualResult, is(expectedResult));
        assertEquals(actualResult.getAnswers(), expectedResult.getAnswers());
        assertThat(actualResult.getQuiz(), equalTo(expectedResult.getQuiz()));
        verify(questionRepository).save(any());
        verify(answerService, never()).saveAll(any());
    }

    @Test
    void testGetQuizById() {
        QuestionForm questionForSave = QuestionUtils.generateQuestionFormWithAnswers();
        Question savedQuestion = FormUtils.toQuestion(questionForSave)
                .toBuilder()
                .id(LONG_ONE)
                .build();
        when(questionRepository.save(any())).thenReturn(savedQuestion);
        when(questionRepository.findById(eq(LONG_ONE))).thenReturn(savedQuestion);
        when(answerService.getByQuestionId(LONG_ONE)).thenReturn(savedQuestion.getAnswers());
        when(answerService.saveAll(savedQuestion.getAnswers())).thenReturn(savedQuestion.getAnswers());

        Question expectedResult = questionService.save(questionForSave);
        Question actualResult = questionService.getById(expectedResult.getId());

        assertThat(actualResult, notNullValue());
        assertThat(actualResult, is(expectedResult));
        assertEquals(actualResult.getAnswers(), expectedResult.getAnswers());

        verify(questionRepository).save(any());
        verify(questionRepository).findById(LONG_ONE);
        verify(answerService).getByQuestionId(LONG_ONE);
        verify(answerService).saveAll(expectedResult.getAnswers());
    }

    @Test
    void testUpdate() {
        QuestionForm questionForUpdate = QuestionUtils.generateQuestionForm();
        String dataForUpdate = "Title";
        QuestionForm questionWithUpdatedData = questionForUpdate.toBuilder().title(dataForUpdate).build();
        Question expectedResult = FormUtils.toQuestion(questionWithUpdatedData.toBuilder().title(dataForUpdate).build());
        when(questionRepository.update(eq(LONG_ONE), any())).thenReturn(expectedResult);
        Question actualResult = questionService.update(LONG_ONE, questionWithUpdatedData);
        assertThat(actualResult, is(expectedResult));
        assertThat(actualResult.getTitle(), not(questionForUpdate.getTitle()));
        verify(questionRepository).update(eq(LONG_ONE), any());
    }

    @Test
    void testDelete() {
        questionService.delete(LONG_ONE);
        verify(questionRepository).delete(eq(LONG_ONE));
    }

    @Test
    void testSaveAll() {
        int size = 10;
        List<Question> expectedResult = QuestionUtils.generateQuestions(size);
        when(questionRepository.save(any(Question.class))).thenReturn(expectedResult.get(INTEGER_ZERO));
        List<Question> actualResult = questionService.saveAll(expectedResult);
        assertThat(actualResult.size(), is(expectedResult.size()));
        verify(questionRepository, times(size)).save(any(Question.class));
    }
}