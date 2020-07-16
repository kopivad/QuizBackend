package com.kopivad.quizzes.repository;

import com.kopivad.quizzes.domain.QuizSession;

public interface QuizSessionRepository {
    long save(QuizSession quizSession);
    boolean update(QuizSession quizSession);

    QuizSession findById(long sessionId);
}
