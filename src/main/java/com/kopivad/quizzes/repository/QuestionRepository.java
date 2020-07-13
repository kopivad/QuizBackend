package com.kopivad.quizzes.repository;

import com.kopivad.quizzes.domain.Question;

import java.util.List;

public interface QuestionRepository {
    List<Question> findAll();

    Question findById(Long id);

    long save(Question question);

    boolean update(Question question);

    boolean delete(Long id);

    List<Question> findByQuizId(Long id);
}
