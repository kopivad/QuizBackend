package com.kopivad.quizzes.repository;

import com.kopivad.quizzes.domain.QuizHistory;

public interface QuizHistoryRepository {
    QuizHistory save(QuizHistory quizHistory);
}
