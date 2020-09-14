package com.kopivad.quizzes.service;

import com.kopivad.quizzes.domain.QuizHistory;
import org.springframework.core.io.Resource;

import java.util.List;
import java.util.Optional;


public interface QuizHistoryService {
    long save(QuizHistory quizHistory);

    Optional<Resource> getPDF(long id);

    Optional<Resource> getCSV(long id);

    long createHistory(long sessionId);

    Optional<QuizHistory> getById(long id);

    List<QuizHistory> getAll();

    List<QuizHistory> getAllBySessionId(long sessionId);
}
