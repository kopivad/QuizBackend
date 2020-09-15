package com.kopivad.quizzes.service;

import com.kopivad.quizzes.domain.QuizSession;
import com.kopivad.quizzes.dto.FullQuizSessionDto;
import com.kopivad.quizzes.dto.SaveQuizSessionDto;

import java.util.Optional;

public interface QuizSessionService {

    long save(SaveQuizSessionDto saveQuizSessionDto);

    Optional<FullQuizSessionDto> getFullById(long id);

    Optional<QuizSession> getById(long sessionId);
}
