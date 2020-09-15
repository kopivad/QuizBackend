package com.kopivad.quizzes.service;

import com.kopivad.quizzes.domain.Answer;
import com.kopivad.quizzes.dto.AnswerDto;

import java.util.List;
import java.util.Optional;

public interface AnswerService {
    List<Answer> getAll();

    Optional<Answer> getById(Long id);

    boolean save(AnswerDto dto);

    boolean update(Answer dto);

    boolean delete(Long id);

    List<Answer> getByQuestionId(Long id);

    boolean saveAll(List<AnswerDto> answer);
}
