package com.kopivad.quizzes.service;

import com.kopivad.quizzes.domain.QuizAnswer;
import com.kopivad.quizzes.dto.QuizAnswerDto;

import java.util.List;

public interface QuizAnswerService {
    long save(QuizAnswerDto quizAnswerDto);

    List<QuizAnswer> getAllBySessionId(long sessionId);
}
