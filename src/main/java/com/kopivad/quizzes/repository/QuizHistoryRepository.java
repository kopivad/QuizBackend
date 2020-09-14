package com.kopivad.quizzes.repository;

import com.kopivad.quizzes.domain.QuizHistory;

import java.util.List;
import java.util.Optional;

public interface QuizHistoryRepository {
    long save(QuizHistory quizHistory);

    Optional<QuizHistory> findById(long id);

    List<QuizHistory> findAll();

    List<QuizHistory> findAllBySessionId(long sessionId);
}
