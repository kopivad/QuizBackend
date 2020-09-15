package com.kopivad.quizzes.service;

import com.kopivad.quizzes.domain.Question;
import com.kopivad.quizzes.dto.FullQuestionDto;
import com.kopivad.quizzes.dto.QuestionDto;

import java.util.List;
import java.util.Optional;

public interface QuestionService {
    List<Question> getAll();

    Optional<FullQuestionDto> getById(Long id);

    boolean save(QuestionDto dto);

    boolean update(Question question);

    boolean delete(Long id);

    List<FullQuestionDto> getFullByQuizId(Long id);

    boolean saveAll(List<QuestionDto> questions);
}
