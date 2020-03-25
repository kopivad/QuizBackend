package com.kopivad.testingsystem.repository;

import com.kopivad.testingsystem.domain.Question;

import java.util.List;

public interface QuestionRepository {
    List<Question> findAll();

    Question findById(Long id);

    Question save(Question question);

    Question update(Long id, Question question);

    void delete(Long id);
}
