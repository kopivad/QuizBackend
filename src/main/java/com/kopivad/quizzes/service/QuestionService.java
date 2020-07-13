package com.kopivad.quizzes.service;

import com.kopivad.quizzes.domain.Question;
import com.kopivad.quizzes.dto.QuestionDto;

import java.util.List;

public interface QuestionService {
    List<QuestionDto> getAll();

    Question getById(Long id);

    long save(QuestionDto questionDto);

    boolean update(QuestionDto questionDto);

    boolean delete(Long id);

    List<Question> getByQuizId(Long id);

    void saveAll(List<QuestionDto> dtos);
}
