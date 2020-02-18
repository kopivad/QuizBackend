package com.kopivad.testingsystem.repository;

import com.kopivad.testingsystem.domain.Quiz;
import org.springframework.data.repository.NoRepositoryBean;

import java.util.List;

@NoRepositoryBean
public interface QuizRepository {
    List<Quiz> findAll();
    Quiz saveQuiz(Quiz quiz);
    Quiz findQuizById(Long id);
    Quiz updateQuiz(Quiz quiz);
    List<Quiz> findAllByAuthorId(Long id);
    void deleteQuizById(Long id);
}
