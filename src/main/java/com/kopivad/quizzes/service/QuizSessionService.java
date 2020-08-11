package com.kopivad.quizzes.service;

import com.kopivad.quizzes.dto.QuizSessionDto;

public interface QuizSessionService {

    long startSession(QuizSessionDto quizSessionDto);

    QuizSessionDto getById(long sessionId);
}
