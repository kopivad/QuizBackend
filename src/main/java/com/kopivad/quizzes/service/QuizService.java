package com.kopivad.quizzes.service;

import com.kopivad.quizzes.domain.Quiz;
import com.kopivad.quizzes.dto.FullQuizDto;
import com.kopivad.quizzes.dto.QuizDto;

import java.util.List;
import java.util.Optional;

public interface QuizService {
    List<Quiz> getAll();

    Optional<FullQuizDto> getById(Long id);

    boolean save(QuizDto dto);

    boolean update(Quiz quiz);

    boolean delete(Long id);

    List<Quiz> getByTitleStartsWith(String title);
}
