package com.kopivad.quizzes.repository;

import com.kopivad.quizzes.domain.Quiz;

import java.util.List;

public interface QuizRepository {
    List<Quiz> findAll();

    Quiz findById(Long id);

    long save(Quiz quiz);

    boolean update(Quiz quiz);

    boolean delete(Long id);
}
