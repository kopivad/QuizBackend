package com.kopivad.quizzes.service;

import com.kopivad.quizzes.domain.QuizHistory;
import com.kopivad.quizzes.dto.QuizHistoryDto;

import java.io.FileNotFoundException;
import java.util.List;
import java.util.Optional;


public interface QuizHistoryService {
    long save(QuizHistoryDto dto);

    Optional<byte[]> getPDF(long id) throws FileNotFoundException;

    Optional<byte[]> getCSV(long id) throws FileNotFoundException;

    Optional<Long> createHistory(long sessionId);

    Optional<QuizHistory> getById(long id);

    List<QuizHistory> getAll();
}
