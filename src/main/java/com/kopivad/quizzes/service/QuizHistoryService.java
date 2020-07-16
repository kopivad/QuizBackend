package com.kopivad.quizzes.service;

import com.kopivad.quizzes.domain.QuizHistory;
import com.kopivad.quizzes.dto.QuizHistoryDto;
import org.springframework.core.io.Resource;

import java.util.List;


public interface QuizHistoryService {
    long save(QuizHistory quizHistory);

    Resource getPDF(long id);

    Resource getCSV(long id);

    long createHistory(long sessionId);

    QuizHistoryDto getById(long id);

    List<QuizHistoryDto> getAll();
}
