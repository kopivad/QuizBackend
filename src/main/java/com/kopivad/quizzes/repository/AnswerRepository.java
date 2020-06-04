package com.kopivad.quizzes.repository;

import com.kopivad.quizzes.domain.Answer;

import java.util.List;

public interface AnswerRepository {
    List<Answer> findAll();

    Answer findById(Long id);

    Answer save(Answer answer);

    Answer update(Long id, Answer answer);

    void delete(Long id);

    List<Answer> findByQuestionId(Long id);
}
