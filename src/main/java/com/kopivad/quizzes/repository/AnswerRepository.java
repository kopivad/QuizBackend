package com.kopivad.quizzes.repository;

import com.kopivad.quizzes.domain.Answer;
import com.kopivad.quizzes.dto.AnswerDto;

import java.util.List;
import java.util.Optional;

public interface AnswerRepository {
    List<Answer> findAll();

    Optional<Answer> findById(Long id);

    int save(AnswerDto answer);

    int update(Answer answer);

    int delete(Long id);

    List<Answer> findByQuestionId(Long id);

    int saveAll(List<AnswerDto> answers);
}
