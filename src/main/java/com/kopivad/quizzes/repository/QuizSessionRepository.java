package com.kopivad.quizzes.repository;

import com.kopivad.quizzes.domain.QuizSession;
import com.kopivad.quizzes.dto.QuizSessionDto;

import java.util.Optional;

public interface QuizSessionRepository {
    long save(QuizSessionDto quizSession);

    int update(QuizSession quizSession);

    Optional<QuizSession> findById(long sessionId);
}
