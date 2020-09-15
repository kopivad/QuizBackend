package com.kopivad.quizzes.service;

import com.kopivad.quizzes.domain.QuizSession;
import com.kopivad.quizzes.dto.QuizSessionDto;

import java.util.Optional;

public interface QuizSessionService {

    long save(QuizSessionDto quizSessionDto);

    Optional<QuizSession> getById(long sessionId);
}
