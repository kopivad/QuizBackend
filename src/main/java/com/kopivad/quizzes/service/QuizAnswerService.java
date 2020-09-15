package com.kopivad.quizzes.service;

import com.kopivad.quizzes.domain.QuizAnswer;
import com.kopivad.quizzes.dto.SaveQuizAnswerDto;

import java.util.List;

public interface QuizAnswerService {
    boolean save(SaveQuizAnswerDto saveQuizAnswerDto);

    List<QuizAnswer> getAllBySessionId(long sessionId);
}
