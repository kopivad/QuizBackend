package com.kopivad.quizzes.repository;

import com.kopivad.quizzes.domain.Question;
import com.kopivad.quizzes.dto.QuestionDto;

import java.util.List;
import java.util.Optional;

public interface QuestionRepository {
    List<Question> findAll();

    Optional<Question> findById(Long id);

    long save(QuestionDto question);

    int update(Question question);

    int delete(Long id);

    List<Question> findByQuizId(Long id);

    int saveAll(List<QuestionDto> dtos);
}
