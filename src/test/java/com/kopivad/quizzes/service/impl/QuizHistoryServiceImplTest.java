package com.kopivad.quizzes.service.impl;

import com.kopivad.quizzes.domain.Answer;
import com.kopivad.quizzes.domain.EvaluationStep;
import com.kopivad.quizzes.domain.QuizHistory;
import com.kopivad.quizzes.domain.QuizSession;
import com.kopivad.quizzes.dto.FullQuestionDto;
import com.kopivad.quizzes.dto.QuizHistoryDto;
import com.kopivad.quizzes.repository.QuizHistoryRepository;
import com.kopivad.quizzes.service.*;
import com.kopivad.quizzes.utils.*;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

class QuizHistoryServiceImplTest {
    private final String fileDir = "C:/Users/Vadym/Projects/QuizzesBackend/src/test/resources/static/media/";
    private final QuizHistoryRepository quizHistoryRepository = mock(QuizHistoryRepository.class);
    private final QuizSessionService quizSessionService = mock(QuizSessionService.class);
    private final QuizAnswerService quizAnswerService = mock(QuizAnswerService.class);
    private final AnswerService answerService = mock(AnswerService.class);
    private final QuestionService questionService = mock(QuestionService.class);
    private final EvaluationStepService stepService = mock(EvaluationStepService.class);
    private final QuizHistoryServiceImpl quizHistoryService = new QuizHistoryServiceImpl(fileDir, quizHistoryRepository, quizSessionService, quizAnswerService, answerService, questionService, stepService);

    @Test
    void saveTest() {
        QuizHistoryDto dto = QuizHistoryUtils.generateHistoryDto();
        when(quizHistoryRepository.save(any(QuizHistoryDto.class))).thenReturn(QuizHistoryUtils.TEST_HISTORY_ID);
        long actual = quizHistoryService.save(dto);

        assertThat(actual, notNullValue());

        verify(quizHistoryRepository).save(any(QuizHistoryDto.class));
    }

    @Test
    void getPdfTest() {
        QuizHistory history = QuizHistoryUtils.generateHistory();
        when(quizHistoryRepository.findById(anyLong())).thenReturn(Optional.of(history));

        Optional<byte[]> actual = quizHistoryService.getPDF(history.getId());

        assertThat(actual.isPresent(), is(true));

        verify(quizHistoryRepository).findById(anyLong());
    }

    @Test
    void getCsvTest() {
        QuizHistory history = QuizHistoryUtils.generateHistory();
        when(quizHistoryRepository.findById(anyLong())).thenReturn(Optional.of(history));
        Optional<byte[]> actual = quizHistoryService.getCSV(history.getId());

        assertThat(actual.isPresent(), is(true));

        verify(quizHistoryRepository).findById(anyLong());
    }

    @Test
    void createHistoryTest() {
        int count = 4;
        QuizHistory history = QuizHistoryUtils.generateHistory();
        QuizSession session = QuizSessionUtils.generateQuizSession(QuizSessionUtils.TEST_SESSION_ID);
        List<EvaluationStep> evaluationSteps = EvaluationStepUtils.generateSteps(4);
        FullQuestionDto fullQuestionDto = QuestionUtils.generateFullQuestionDto();
        Answer answer = AnswerUtils.generateAnswer();
        when(quizSessionService.getById(anyLong())).thenReturn(Optional.of(session));
        when(stepService.getByQuizId(anyLong())).thenReturn(evaluationSteps);
        when(answerService.getById(anyLong())).thenReturn(Optional.of(answer));
        when(questionService.getById(anyLong())).thenReturn(Optional.of(fullQuestionDto));
        when(quizHistoryRepository.save(any(QuizHistoryDto.class))).thenReturn(QuizHistoryUtils.TEST_HISTORY_ID);
        when(quizHistoryRepository.findById(anyLong())).thenReturn(Optional.of(history));
        when(quizAnswerService.getAllBySessionId(anyLong())).thenReturn(QuizAnswerUtils.generateQuizAnswers(count));

        Optional<Long> actual = quizHistoryService.createHistory(session.getId());

        assertThat(actual.isPresent(), is(true));

        verify(quizSessionService).getById(anyLong());
        verify(quizHistoryRepository, times(2)).findById(anyLong());
        verify(quizHistoryRepository).save(any(QuizHistoryDto.class));
        verify(quizAnswerService).getAllBySessionId(anyLong());
        verify(stepService).getByQuizId(anyLong());
        verify(answerService, times(count)).getById(anyLong());
        verify(answerService, times(count)).getById(anyLong());

    }

    @Test
    void getByIdTest() {
        QuizHistory expected = QuizHistoryUtils.generateHistory();
        when(quizHistoryRepository.findById(anyLong())).thenReturn(Optional.of(expected));

        Optional<QuizHistory> actual = quizHistoryService.getById(expected.getId());

        assertThat(actual.isPresent(), is(true));

        verify(quizHistoryRepository).findById(anyLong());
    }

    @Test
    void getAllTest() {
        int size = 10;
        List<QuizHistory> expected = QuizHistoryUtils.generateHistories(size);
        when(quizHistoryRepository.findAll()).thenReturn(expected);

        List<QuizHistory> actual = quizHistoryService.getAll();

        assertThat(actual.size(), is(expected.size()));

        verify(quizHistoryRepository).findAll();
    }
}