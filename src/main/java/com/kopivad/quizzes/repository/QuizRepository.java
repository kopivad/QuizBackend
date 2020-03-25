package com.kopivad.quizzes.repository;

import com.kopivad.quizzes.domain.Quiz;

import java.util.List;

public interface QuizRepository {
    List<Quiz> findAll();

    Quiz findById(Long id);

    Quiz save(Quiz quiz);

    Quiz update(Long id, Quiz quiz);

    void delete(Long id);
}
