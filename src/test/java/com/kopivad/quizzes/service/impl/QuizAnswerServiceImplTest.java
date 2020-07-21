package com.kopivad.quizzes.service.impl;

import com.kopivad.quizzes.domain.QuizAnswer;
import com.kopivad.quizzes.dto.QuizAnswerDto;
import com.kopivad.quizzes.mapper.QuizAnswerMapper;
import com.kopivad.quizzes.repository.QuizAnswerRepository;
import com.kopivad.quizzes.service.AnswerService;
import com.kopivad.quizzes.service.QuestionService;
import com.kopivad.quizzes.utils.AnswerUtils;
import com.kopivad.quizzes.utils.QuestionUtils;
import com.kopivad.quizzes.utils.QuizAnswerUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.List;

import static org.apache.commons.lang3.math.NumberUtils.LONG_ONE;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
class QuizAnswerServiceImplTest {
    @InjectMocks
    private QuizAnswerServiceImpl quizAnswerService;
    @Mock
    private QuizAnswerRepository quizAnswerRepository;
    @Mock
    private QuizAnswerMapper mapper;
    @Mock
    private QuestionService questionService;
    @Mock
    private AnswerService answerService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void testSave() {
        QuizAnswerDto expected = QuizAnswerUtils.generateAnswerDto();
        when(mapper.toEntity(any(QuizAnswerDto.class))).thenReturn(QuizAnswerUtils.generateAnswer());
        when(quizAnswerRepository.save(any(QuizAnswer.class))).thenReturn(expected.getId());
        long actual = quizAnswerService.save(expected);

        assertThat(actual, is(expected.getId()));

        verify(mapper).toEntity(any(QuizAnswerDto.class));
        verify(quizAnswerRepository).save(any(QuizAnswer.class));
    }

    @Test
    void testGetAllBySessionId() {
        int count = 10;
        List<QuizAnswer> expected = QuizAnswerUtils.generateAnswers(count);
        when(quizAnswerRepository.findAllBySessionId(anyLong())).thenReturn(expected);
        when(questionService.getById(anyLong())).thenReturn(QuestionUtils.generateQuestion());
        when(answerService.getById(anyLong())).thenReturn(AnswerUtils.generateAnswer());
        List<QuizAnswer> actual = quizAnswerService.getAllBySessionId(LONG_ONE);

        assertThat(count, is(actual.size()));

        verify(quizAnswerRepository).findAllBySessionId(anyLong());
        verify(questionService, times(count)).getById(anyLong());
        verify(answerService, times(count)).getById(anyLong());
    }
}