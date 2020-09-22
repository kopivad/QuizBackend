package com.kopivad.quizzes.service.impl;


import com.kopivad.quizzes.domain.Answer;
import com.kopivad.quizzes.domain.Question;
import com.kopivad.quizzes.dto.FullQuestionDto;
import com.kopivad.quizzes.dto.QuestionDto;
import com.kopivad.quizzes.repository.QuestionRepository;
import com.kopivad.quizzes.service.AnswerService;
import com.kopivad.quizzes.service.QuestionService;
import com.kopivad.quizzes.utils.AnswerUtils;
import com.kopivad.quizzes.utils.QuestionUtils;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.apache.commons.lang3.math.NumberUtils.INTEGER_ONE;
import static org.apache.commons.lang3.math.NumberUtils.LONG_ONE;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

class QuestionServiceImplTest {
    private final QuestionRepository questionRepository = mock(QuestionRepository.class);
    private final AnswerService answerService = mock(AnswerService.class);
    private final QuestionService questionService = new QuestionServiceImpl(questionRepository, answerService);

    @Test
    void getAllTest() {
        int size = 10;
        List<Question> expected = QuestionUtils.generateQuestions(size);
        when(questionRepository.findAll()).thenReturn(expected);
        List<Question> actual = questionService.getAll();

        assertThat(actual.size(), is(expected.size()));

        verify(questionRepository).findAll();
    }

    @Test
    void saveTest() {
        QuestionDto expected = QuestionUtils.generateQuestionDto();
        when(questionRepository.save(any())).thenReturn(LONG_ONE);
        when(answerService.saveAll(anyList())).thenReturn(true);
        boolean actual = questionService.save(expected);

        assertThat(actual, is(true));

        verify(questionRepository).save(any(QuestionDto.class));
        verify(answerService).saveAll(anyList());
    }

    @Test
    void getByIdTest() {
        int size = 10;
        Question question = QuestionUtils.generateQuestion();
        List<Answer> answers = AnswerUtils.generateAnswers(size);
        when(questionRepository.findById(anyLong())).thenReturn(Optional.of(question));
        when(answerService.getByQuestionId(anyLong())).thenReturn(answers);
        Optional<FullQuestionDto> actual = questionService.getById(question.getId());

        assertThat(actual.isPresent(), is(true));

        verify(questionRepository).findById(anyLong());
        verify(answerService).getByQuestionId(anyLong());
    }

    @Test
    void updateTest() {
        Question expected = QuestionUtils.generateQuestion();
        when(questionRepository.update(any(Question.class))).thenReturn(INTEGER_ONE);

        boolean actual = questionService.update(expected);

        assertThat(actual, is(true));

        verify(questionRepository).update(any(Question.class));
    }

    @Test
    void deleteTest() {
        long expected = QuestionUtils.generateQuestion().getId();
        when(questionRepository.delete(anyLong())).thenReturn(INTEGER_ONE);
        boolean actual = questionService.delete(expected);

        assertTrue(actual);

        verify(questionRepository).delete(anyLong());
    }

    @Test
    void saveAllTest() {
        int size = 10;
        List<QuestionDto> expectedResult = QuestionUtils.generateQuestionDtos(size);
        when(questionRepository.saveAll(anyList())).thenReturn(size);
        boolean actual = questionService.saveAll(expectedResult);

        assertThat(actual, is(true));

        verify(questionRepository).saveAll(anyList());
    }

    @Test
    public void getFullByQuizIdTest() {
        int expected = 10;
        List<Question> questions = QuestionUtils.generateQuestions(expected);
        List<Answer> answers = AnswerUtils.generateAnswers(expected);
        when(questionRepository.findByQuizId(anyLong())).thenReturn(questions);
        when(answerService.getByQuestionId(anyLong())).thenReturn(answers);
        List<FullQuestionDto> actual = questionService.getFullByQuizId(LONG_ONE);

        assertThat(actual.size(), is(expected));

        verify(questionRepository).findByQuizId(anyLong());
        verify(answerService, times(expected)).getByQuestionId(anyLong());
    }
}