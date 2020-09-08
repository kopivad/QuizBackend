package com.kopivad.quizzes.service.impl;

import com.kopivad.quizzes.domain.Answer;
import com.kopivad.quizzes.dto.AnswerDto;
import com.kopivad.quizzes.mapper.AnswerMapper;
import com.kopivad.quizzes.repository.AnswerRepository;
import com.kopivad.quizzes.utils.AnswerUtils;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.apache.commons.lang3.math.NumberUtils.LONG_ONE;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@SpringBootTest
class AnswerServiceImplTest {
    @InjectMocks
    private AnswerServiceImpl answerService;

    @Mock
    private AnswerRepository answerRepository;

    @Mock
    private AnswerMapper answerMapper;

    @Test
    void testGetAll() {
        int size = 10;
        List<Answer> expectedResult = AnswerUtils.generateAnswers(size);
        when(answerRepository.findAll()).thenReturn(expectedResult);
        when(answerMapper.toDto(any(Answer.class))).thenReturn(AnswerUtils.generateAnswerDto());
        List<AnswerDto> actualResult = answerService.getAll();

        assertThat(actualResult.size(), is(expectedResult.size()));

        verify(answerRepository).findAll();
        verify(answerMapper, times(size)).toDto(any(Answer.class));
    }

    @Test
    void testGetById() {
        Answer expectedResult = AnswerUtils.generateAnswer();
        when(answerRepository.findById(anyLong())).thenReturn(expectedResult);
        Answer actualResult = answerService.getById(expectedResult.getId());

        assertThat(actualResult, is(expectedResult));

        verify(answerRepository).findById(anyLong());
    }

    @Test
    void testSave() {
        AnswerDto expectedResult = AnswerUtils.generateAnswerDto();
        when(answerMapper.toEntity(any(AnswerDto.class))).thenReturn(AnswerUtils.generateAnswer());
        when(answerRepository.save(any(Answer.class))).thenReturn(expectedResult.getId());
        long actualResult = answerService.save(expectedResult);

        assertThat(actualResult, is(expectedResult.getId()));

        verify(answerRepository).save(any(Answer.class));
        verify(answerMapper).toEntity(any(AnswerDto.class));
    }

    @Test
    void testUpdate() {
        AnswerDto expectedResult = AnswerUtils.generateAnswerDto();
        when(answerRepository.update(any(Answer.class))).thenReturn(true);
        when(answerMapper.toEntity(any(AnswerDto.class))).thenReturn(AnswerUtils.generateAnswer());
        boolean actualResult = answerService.update(expectedResult);

        assertTrue(actualResult);

        verify(answerRepository).update(any(Answer.class));
        verify(answerMapper).toEntity(any(AnswerDto.class));
    }

    @Test
    void testDelete() {
        Long expected = AnswerUtils.generateAnswer().getId();
        when(answerRepository.delete(anyLong())).thenReturn(true);
        boolean actual = answerService.delete(expected);

        assertTrue(actual);

        verify(answerRepository).delete(anyLong());
    }

    @Test
    void testSaveAll() {
        int size = 10;
        List<Answer> expectedResult = AnswerUtils.generateAnswers(size);
        when(answerRepository.save(any(Answer.class))).thenReturn(LONG_ONE);
        answerService.saveAll(expectedResult);

        assertThat(size, is(expectedResult.size()));

        verify(answerRepository, times(size)).save(any(Answer.class));
    }

    @Test
    void testGetByQuestionId() {
        int size = 10;
        List<Answer> expectedResult = AnswerUtils.generateAnswers(size);
        when(answerRepository.findByQuestionId(anyLong())).thenReturn(expectedResult);
        List<Answer> actualResult = answerService.getByQuestionId(LONG_ONE);

        assertThat(actualResult.size(), is(expectedResult.size()));

        verify(answerRepository).findByQuestionId(anyLong());
    }
}