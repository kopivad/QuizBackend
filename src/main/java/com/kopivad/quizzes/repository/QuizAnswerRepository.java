package com.kopivad.quizzes.repository;

import com.kopivad.quizzes.domain.QuizAnswer;

import java.util.List;

public interface QuizAnswerRepository {
    boolean save(QuizAnswer quizAnswer);

    List<QuizAnswer> findAllBySessionId(long sessionId);
}
