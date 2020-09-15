package com.kopivad.quizzes.repository;

import com.kopivad.quizzes.domain.Answer;

import java.util.List;
import java.util.Optional;

public interface AnswerRepository {
    List<Answer> findAll();

    Optional<Answer> findById(Long id);

    boolean save(Answer answer);

    boolean update(Answer answer);

    boolean delete(Long id);

    List<Answer> findByQuestionId(Long id);
}
