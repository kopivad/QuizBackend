package com.kopivad.quizzes.repository;

import com.kopivad.quizzes.domain.QuizSession;

public interface QuizSessionRepository {
    long save(QuizSession quizSession);
    QuizSession update(QuizSession quizSession);
}
