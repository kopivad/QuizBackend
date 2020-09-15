package com.kopivad.quizzes.repository;

import com.kopivad.quizzes.domain.Quiz;
import com.kopivad.quizzes.dto.QuizDto;

import java.util.List;
import java.util.Optional;

public interface QuizRepository {
    List<Quiz> findAll();

    Optional<Quiz> findById(Long id);

    long save(QuizDto quiz);

    int update(Quiz quiz);

    int delete(Long id);

    List<Quiz> findByGroupId(long id);

    List<Quiz> findByTitleStartsWith(String title);
}
