package com.kopivad.quizzes.service.impl;

import com.kopivad.quizzes.domain.Answer;
import com.kopivad.quizzes.dto.AnswerDto;
import com.kopivad.quizzes.repository.AnswerRepository;
import com.kopivad.quizzes.utils.AnswerUtils;
import com.kopivad.quizzes.utils.QuestionUtils;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.apache.commons.lang3.math.NumberUtils.INTEGER_ONE;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.*;

class AnswerServiceImplTest {
    private final AnswerRepository answerRepository = mock(AnswerRepository.class);
    private final AnswerServiceImpl answerService = new AnswerServiceImpl(answerRepository);

    @Test
    void getAllTest() {
        int size = 10;
        List<Answer> expected = AnswerUtils.generateAnswers(size);
        when(answerRepository.findAll()).thenReturn(expected);
        List<Answer> actual = answerService.getAll();

        assertThat(actual.size(), is(expected.size()));

        verify(answerRepository).findAll();
    }

    @Test
    void getByIdTest() {
        Answer expected = AnswerUtils.generateAnswer();
        when(answerRepository.findById(anyLong())).thenReturn(Optional.of(expected));
        Optional<Answer> actual = answerService.getById(expected.getId());

        assertThat(actual.isPresent(), is(true));

        verify(answerRepository).findById(anyLong());
    }

    @Test
    void saveTest() {
        AnswerDto expected = AnswerUtils.generateAnswerDto();
        when(answerRepository.save(any(AnswerDto.class))).thenReturn(INTEGER_ONE);
        boolean actual = answerService.save(expected);

        assertThat(actual, is(true));

        verify(answerRepository).save(any(AnswerDto.class));
    }

    @Test
    void updateTest() {
        Answer expected = AnswerUtils.generateAnswer();
        when(answerRepository.update(any(Answer.class))).thenReturn(INTEGER_ONE);
        boolean actual = answerService.update(expected);

        assertThat(actual, is(true));

        verify(answerRepository).update(any(Answer.class));
    }

    @Test
    void deleteTest() {
        Long expected = AnswerUtils.generateAnswer().getId();
        when(answerRepository.delete(anyLong())).thenReturn(INTEGER_ONE);
        boolean actual = answerService.delete(expected);

        assertThat(actual, is(true));

        verify(answerRepository).delete(anyLong());
    }

    @Test
    void saveAllTest() {
        int size = 10;
        List<AnswerDto> expected = AnswerUtils.generateAnswerDtos(size);
        when(answerRepository.saveAll(anyList())).thenReturn(size);
        boolean actual = answerService.saveAll(expected);

        assertThat(actual, is(true));

        verify(answerRepository).saveAll(anyList());
    }

    @Test
    void getByQuestionIdTest() {
        int size = 10;
        List<Answer> expectedResult = AnswerUtils.generateAnswers(size);
        when(answerRepository.findByQuestionId(anyLong())).thenReturn(expectedResult);
        List<Answer> actualResult = answerService.getByQuestionId(QuestionUtils.TEST_QUESTION_ID);

        assertThat(actualResult.size(), is(expectedResult.size()));

        verify(answerRepository).findByQuestionId(anyLong());
    }
}