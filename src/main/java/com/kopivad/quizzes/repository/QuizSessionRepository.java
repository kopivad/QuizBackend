package com.kopivad.quizzes.repository;

import com.kopivad.quizzes.domain.QuizSession;

import java.util.Optional;

public interface QuizSessionRepository {
    long save(QuizSession quizSession);
    boolean update(QuizSession quizSession);

    Optional<QuizSession> findById(long sessionId);
}
