package com.kopivad.testingsystem.repository;

import com.kopivad.testingsystem.domain.Quiz;

import java.util.List;

public interface QuizRepository {
    List<Quiz> findAll();

    Quiz findById(Long id);

    Quiz save(Quiz quiz);

    Quiz update(Long id, Quiz quiz);

    void delete(Long id);
}
