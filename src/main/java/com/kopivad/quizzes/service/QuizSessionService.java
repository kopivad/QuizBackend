package com.kopivad.quizzes.service;

import com.kopivad.quizzes.domain.QuizSession;
import com.kopivad.quizzes.dto.QuizSessionDto;

public interface QuizSessionService {

    long startSession(QuizSessionDto quizSessionDto);

    QuizSession getById(long sessionId);
}
