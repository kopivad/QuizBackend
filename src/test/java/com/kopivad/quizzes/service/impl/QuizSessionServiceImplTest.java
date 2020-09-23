package com.kopivad.quizzes.service.impl;

import com.kopivad.quizzes.domain.QuizSession;
import com.kopivad.quizzes.dto.QuizSessionDto;
import com.kopivad.quizzes.repository.QuizSessionRepository;
import com.kopivad.quizzes.utils.QuizSessionUtils;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.apache.commons.lang3.math.NumberUtils.LONG_ONE;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class QuizSessionServiceImplTest {
    private final QuizSessionRepository quizSessionRepository = mock(QuizSessionRepository.class);
    private final QuizSessionServiceImpl quizSessionService = new QuizSessionServiceImpl(quizSessionRepository);

    @Test
    void saveTest() {
        QuizSessionDto expected = QuizSessionUtils.generateQuizSessionDto();
        when(quizSessionRepository.save(any(QuizSessionDto.class))).thenReturn(LONG_ONE);
        long actual = quizSessionService.save(expected);

        assertThat(actual, notNullValue());

        verify(quizSessionRepository).save(any(QuizSessionDto.class));
    }

    @Test
    void getByIdTest() {
        QuizSession expected = QuizSessionUtils.generateQuizSession(QuizSessionUtils.TEST_SESSION_ID);
        when(quizSessionRepository.findById(anyLong())).thenReturn(Optional.of(expected));
        Optional<QuizSession> actual = quizSessionService.getById(expected.getId());

        assertThat(actual.isPresent(), is(true));

        verify(quizSessionRepository).findById(anyLong());
    }
}