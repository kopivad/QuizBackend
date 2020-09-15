package com.kopivad.quizzes.repository;

import com.kopivad.quizzes.domain.QuizAnswer;
import com.kopivad.quizzes.dto.QuizAnswerDto;

import java.util.List;

public interface QuizAnswerRepository {
    int save(QuizAnswerDto quizAnswer);

    List<QuizAnswer> findAllBySessionId(long sessionId);
}
