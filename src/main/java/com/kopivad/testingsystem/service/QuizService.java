package com.kopivad.testingsystem.service;

import com.kopivad.testingsystem.domain.Quiz;

import java.util.List;

public interface QuizService {
    List<Quiz> getAll();

    Quiz getById(Long id);

    Quiz save(Quiz quiz);

    Quiz update(Long id, Quiz quiz);

    void delete(Long id);
}
