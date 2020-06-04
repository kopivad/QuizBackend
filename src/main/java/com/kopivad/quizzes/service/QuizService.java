package com.kopivad.quizzes.service;

import com.kopivad.quizzes.domain.Quiz;
import com.kopivad.quizzes.form.QuizForm;

import java.util.List;

public interface QuizService {
    List<Quiz> getAll();

    Quiz getById(Long id);

    Quiz save(QuizForm quizForm);

    Quiz update(Long id, QuizForm quizForm);

    void delete(Long id);
}
