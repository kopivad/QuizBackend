package com.kopivad.quizzes.repository;

import com.kopivad.quizzes.domain.Answer;

import java.util.List;

public interface AnswerRepository {
    List<Answer> findAll();

    Answer findById(Long id);

    long save(Answer answer);

    boolean update(Answer answer);

    boolean delete(Long id);

    List<Answer> findByQuestionId(Long id);

    boolean saveAll(List<Answer> answers);
}
