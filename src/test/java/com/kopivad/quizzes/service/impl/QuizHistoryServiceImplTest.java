package com.kopivad.quizzes.service.impl;

import com.kopivad.quizzes.domain.QuizHistory;
import com.kopivad.quizzes.domain.QuizSession;
import com.kopivad.quizzes.dto.QuizHistoryDto;
import com.kopivad.quizzes.dto.QuizSessionDto;
import com.kopivad.quizzes.mapper.QuizHistoryMapper;
import com.kopivad.quizzes.repository.QuizHistoryRepository;
import com.kopivad.quizzes.service.QuizAnswerService;
import com.kopivad.quizzes.service.QuizSessionService;
import com.kopivad.quizzes.utils.QuizAnswerUtils;
import com.kopivad.quizzes.utils.QuizHistoryUtils;
import com.kopivad.quizzes.utils.QuizSessionUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.core.io.Resource;

import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
class QuizHistoryServiceImplTest {
    @InjectMocks
    private QuizHistoryServiceImpl quizHistoryService;
    @Mock
    private QuizHistoryRepository quizHistoryRepository;
    @Mock
    private QuizSessionService quizSessionService;
    @Mock
    private QuizAnswerService quizAnswerService;
    @Mock
    private QuizHistoryMapper quizHistoryMapper;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void testSave() {
        QuizHistory expected = QuizHistoryUtils.generateHistory();
        when(quizHistoryRepository.save(any(QuizHistory.class))).thenReturn(expected.getId());
        long actual = quizHistoryService.save(expected);

        assertThat(actual, is(expected.getId()));

        verify(quizHistoryRepository).save(any(QuizHistory.class));
    }

    @Test
    void testGetPDF() {
        String exitingFilename = "test.pdf";
        QuizHistory history = QuizHistoryUtils.generateHistory().toBuilder().pdfFilename(exitingFilename).build();
        when(quizHistoryRepository.findById(anyLong())).thenReturn(history);
        Resource actual = quizHistoryService.getPDF(history.getId());

        assertThat(actual, notNullValue());

        verify(quizHistoryRepository).findById(anyLong());
    }

    @Test
    void testGetCSV() {
        String exitingFilename = "test.csv";
        QuizHistory history = QuizHistoryUtils.generateHistory().toBuilder().csvFilename(exitingFilename).build();
        when(quizHistoryRepository.findById(anyLong())).thenReturn(history);
        Resource actual = quizHistoryService.getCSV(history.getId());

        assertThat(actual, notNullValue());

        verify(quizHistoryRepository).findById(anyLong());
    }

    @Test
    void testCreateHistory() {
        int count = 4;
        QuizHistory history = QuizHistoryUtils.generateHistory();
        QuizSessionDto session = QuizSessionUtils.generateQuizSessionDto();
        when(quizSessionService.getById(anyLong())).thenReturn(session);
        when(quizHistoryRepository.save(any(QuizHistory.class))).thenReturn(history.getId());
        when(quizHistoryRepository.findById(anyLong())).thenReturn(history);
        when(quizAnswerService.getAllBySessionId(anyLong())).thenReturn(QuizAnswerUtils.generateAnswerDtos(count));
        long actual = quizHistoryService.createHistory(session.getId());

        assertThat(actual, notNullValue());

        verify(quizSessionService).getById(anyLong());
        verify(quizHistoryRepository, times(2)).findById(anyLong());
        verify(quizHistoryRepository).save(any(QuizHistory.class));
        verify(quizAnswerService).getAllBySessionId(anyLong());

    }

    @Test
    void testGetById() {
        QuizHistory expected = QuizHistoryUtils.generateHistory();
        when(quizHistoryRepository.findById(anyLong())).thenReturn(expected);
        QuizHistory actual = quizHistoryService.getById(expected.getId());

        assertThat(actual, is(expected));

        verify(quizHistoryRepository).findById(anyLong());
    }

    @Test
    void testGetAll() {
        int size = 10;
        List<QuizHistory> expected = QuizHistoryUtils.generateHistories(size);
        when(quizHistoryRepository.findAll()).thenReturn(expected);
        when(quizHistoryMapper.toDto(any(QuizHistory.class))).thenReturn(QuizHistoryUtils.generateHistoryDto());
        List<QuizHistoryDto> actual = quizHistoryService.getAll();

        assertThat(actual.size(), is(expected.size()));

        verify(quizHistoryRepository).findAll();
        verify(quizHistoryMapper, times(size)).toDto(any(QuizHistory.class));
    }
}