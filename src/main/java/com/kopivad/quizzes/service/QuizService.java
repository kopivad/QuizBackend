package com.kopivad.quizzes.service;

import com.kopivad.quizzes.domain.Quiz;
import com.kopivad.quizzes.dto.QuizDto;

import java.util.List;

public interface QuizService {
    List<QuizDto> getAll();

    Quiz getById(Long id);

    long save(QuizDto quizDto);

    boolean update(QuizDto quizDto);

    boolean delete(Long id);

    List<Quiz> getByGroupId(long id);

    List<QuizDto> getByTitleStartsWith(String title);
}
