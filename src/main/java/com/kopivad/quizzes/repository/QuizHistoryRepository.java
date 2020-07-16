package com.kopivad.quizzes.repository;

import com.kopivad.quizzes.domain.QuizHistory;

import java.util.List;

public interface QuizHistoryRepository {
    long save(QuizHistory quizHistory);

    QuizHistory findById(long id);

    List<QuizHistory> findAll();
}
