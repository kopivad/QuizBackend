package com.kopivad.quizzes.service.impl;

import com.kopivad.quizzes.domain.QuizAnswer;
import com.kopivad.quizzes.dto.QuizAnswerDto;
import com.kopivad.quizzes.repository.QuizAnswerRepository;
import com.kopivad.quizzes.service.QuizAnswerService;
import com.kopivad.quizzes.utils.QuizAnswerUtils;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.apache.commons.lang3.math.NumberUtils.INTEGER_ONE;
import static org.apache.commons.lang3.math.NumberUtils.LONG_ONE;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

class QuizAnswerServiceImplTest {
    private final QuizAnswerRepository quizAnswerRepository = mock(QuizAnswerRepository.class);
    private final QuizAnswerService quizAnswerService = new QuizAnswerServiceImpl(quizAnswerRepository);

    @Test
    void saveTest() {
        QuizAnswerDto expected = QuizAnswerUtils.generateAnswerDto();
        when(quizAnswerRepository.save(any(QuizAnswerDto.class))).thenReturn(INTEGER_ONE);

        boolean actual = quizAnswerService.save(expected);

        assertThat(actual, is(true));

        verify(quizAnswerRepository).save(any(QuizAnswerDto.class));
    }

    @Test
    void getAllBySessionIdTest() {
        int count = 10;
        List<QuizAnswer> expected = QuizAnswerUtils.generateQuizAnswers(count);
        when(quizAnswerRepository.findAllBySessionId(anyLong())).thenReturn(expected);
        List<QuizAnswer> actual = quizAnswerService.getAllBySessionId(LONG_ONE);

        assertThat(count, is(actual.size()));

        verify(quizAnswerRepository).findAllBySessionId(anyLong());
    }
}