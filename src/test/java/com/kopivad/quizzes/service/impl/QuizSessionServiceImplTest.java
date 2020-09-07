package com.kopivad.quizzes.service.impl;

import com.kopivad.quizzes.domain.QuizSession;
import com.kopivad.quizzes.dto.QuizSessionDto;
import com.kopivad.quizzes.mapper.QuizSessionMapper;
import com.kopivad.quizzes.repository.QuizSessionRepository;
import com.kopivad.quizzes.service.QuizAnswerService;
import com.kopivad.quizzes.service.QuizService;
import com.kopivad.quizzes.utils.QuizAnswerUtils;
import com.kopivad.quizzes.utils.QuizSessionUtils;
import com.kopivad.quizzes.utils.QuizUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

import static org.apache.commons.lang3.math.NumberUtils.LONG_ONE;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
class QuizSessionServiceImplTest {
    @InjectMocks
    private QuizSessionServiceImpl quizSessionService;
    @Mock
    private QuizSessionRepository quizSessionRepository;
    @Mock
    private QuizSessionMapper mapper;
    @Mock
    private QuizAnswerService quizAnswerService;
    @Mock
    private QuizService quizService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void testStartSession() {
        QuizSessionDto expected = QuizSessionUtils.generateQuizSessionDto();
        when(quizSessionRepository.save(any(QuizSession.class))).thenReturn(LONG_ONE);
        when(mapper.toEntity(any(QuizSessionDto.class))).thenReturn(QuizSessionUtils.generateQuizSession());
        long actual = quizSessionService.startSession(expected);

        assertThat(actual, is(expected.getId()));

        verify(quizSessionRepository).save(any(QuizSession.class));
        verify(mapper).toEntity(any(QuizSessionDto.class));
    }

    @Test
    void testGetById() {
        int count = 10;
        QuizSession expected = QuizSessionUtils.generateQuizSession();
        when(quizSessionRepository.findById(anyLong())).thenReturn(QuizSessionUtils.generateQuizSession());
        when(quizService.getById(anyLong())).thenReturn(QuizUtils.generateQuiz());
        when(quizAnswerService.getAllBySessionId(anyLong())).thenReturn(QuizAnswerUtils.generateAnswerDtos(count));
        QuizSessionDto actual = quizSessionService.getById(expected.getId());

        assertThat(actual, is(expected));

        verify(quizSessionRepository).findById(anyLong());
        verify(quizService).getById(anyLong());
        verify(quizAnswerService).getAllBySessionId(anyLong());
    }
}