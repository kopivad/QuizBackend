package com.kopivad.quizzes.repository;

import com.kopivad.quizzes.domain.Quiz;

import java.util.List;
import java.util.Optional;

public interface QuizRepository {
    List<Quiz> findAll();

    Optional<Quiz> findById(Long id);

    long save(Quiz quiz);

    boolean update(Quiz quiz);

    boolean delete(Long id);

    List<Quiz> findByGroupId(long id);

    List<Quiz> findByTitleStartsWith(String title);
}
