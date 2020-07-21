package com.kopivad.quizzes.service.impl;


import com.kopivad.quizzes.domain.Question;
import com.kopivad.quizzes.dto.QuestionDto;
import com.kopivad.quizzes.mapper.QuestionMapper;
import com.kopivad.quizzes.repository.QuestionRepository;
import com.kopivad.quizzes.service.AnswerService;
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
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
class QuestionServiceImplTest {
    @InjectMocks
    private QuestionServiceImpl questionService;
    @Mock
    private QuestionRepository questionRepository;
    @Mock
    private AnswerService answerService;
    @Mock
    private QuestionMapper questionMapper;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void testGetAll() {
        int size = 10;
        List<Question> expectedResult = QuestionUtils.generateQuestions(size);
        when(questionRepository.findAll()).thenReturn(expectedResult);
        when(questionMapper.toDto(any(Question.class))).thenReturn(QuestionUtils.generateQuestionDto());
        List<QuestionDto> actualResult = questionService.getAll();

        assertThat(actualResult.size(), is(expectedResult.size()));

        verify(questionRepository).findAll();
        verify(questionMapper, times(size)).toDto(any(Question.class));
    }

    @Test
    void testSaveQuestionDtoWithFullData() {
        QuestionDto expectedResult = QuestionUtils.generateQuestionDtoWithFullData();
        when(questionMapper.toEntity(any(QuestionDto.class))).thenReturn(QuestionUtils.generateQuestionWithFullData());
        when(questionRepository.save(any())).thenReturn(expectedResult.getId());
        long actualResult = questionService.save(expectedResult);

        assertThat(actualResult, is(expectedResult.getId()));

        verify(questionRepository).save(any(Question.class));
        verify(questionMapper).toEntity(any(QuestionDto.class));
        verify(answerService).saveAll(anyList());
    }

    @Test
    void testSaveQuestionWithNullData() {
        QuestionDto expectedResult = QuestionUtils.generateQuestionDto();
        when(questionMapper.toEntity(any(QuestionDto.class))).thenReturn(QuestionUtils.generateQuestion());
        when(questionRepository.save(any())).thenReturn(expectedResult.getId());

        long actualResult = questionService.save(expectedResult);

        assertThat(actualResult, is(expectedResult.getId()));

        verify(questionRepository).save(any(Question.class));
        verify(questionMapper).toEntity(any(QuestionDto.class));
        verify(answerService, never()).saveAll(anyList());
    }

    @Test
    void testGetQuestionById() {
        Question expected = QuestionUtils.generateQuestionWithFullData();
        when(questionRepository.findById(anyLong())).thenReturn(expected);
        when(answerService.getByQuestionId(anyLong())).thenReturn(expected.getAnswers());
        Question actual = questionService.getById(expected.getId());

        assertThat(actual, is(expected));

        verify(questionRepository).findById(anyLong());
        verify(answerService).getByQuestionId(anyLong());
    }

    @Test
    void testUpdate() {
        QuestionDto expectedResult = QuestionUtils.generateQuestionDto();
        when(questionMapper.toEntity(any(QuestionDto.class))).thenReturn(QuestionUtils.generateQuestion());
        when(questionRepository.update(any(Question.class))).thenReturn(true);

        boolean actualResult = questionService.update(expectedResult);

        assertTrue(actualResult);

        verify(questionRepository).update(any(Question.class));
        verify(questionMapper).toEntity(any(QuestionDto.class));
    }

    @Test
    void testDelete() {
        long expected = QuestionUtils.generateQuestion().getId();
        when(questionRepository.delete(anyLong())).thenReturn(true);
        boolean actual = questionService.delete(expected);

        assertTrue(actual);

        verify(questionRepository).delete(anyLong());
    }

    @Test
    void testSaveAll() {
        int size = 10;
        List<QuestionDto> expectedResult = QuestionUtils.generateQuestionDtos(size);
        when(questionRepository.save(any(Question.class))).thenReturn(LONG_ONE);
        when(questionMapper.toEntity(any(QuestionDto.class))).thenReturn(QuestionUtils.generateQuestion());
        questionService.saveAll(expectedResult);

        assertThat(size, is(expectedResult.size()));

        verify(questionRepository, times(size)).save(any(Question.class));
        verify(questionMapper, times(size)).toEntity(any(QuestionDto.class));
    }
}