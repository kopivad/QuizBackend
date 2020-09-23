package com.kopivad.quizzes.service;

import com.kopivad.quizzes.domain.QuizHistory;
import com.kopivad.quizzes.dto.QuizHistoryDto;
import org.springframework.core.io.Resource;

import java.io.FileNotFoundException;
import java.util.List;
import java.util.Optional;


public interface QuizHistoryService {
    long save(QuizHistoryDto dto);

    Optional<Resource> getPdfResource(long id) throws FileNotFoundException;

    Optional<Resource> getCsvResource(long id) throws FileNotFoundException;

    Optional<Long> createHistory(long sessionId);

    Optional<QuizHistory> getById(long id);

    List<QuizHistory> getAll();
}
