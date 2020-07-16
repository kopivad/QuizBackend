package com.kopivad.quizzes.service;

import com.kopivad.quizzes.domain.Answer;
import com.kopivad.quizzes.dto.AnswerDto;

import java.util.List;

public interface AnswerService {
    List<AnswerDto> getAll();

    Answer getById(Long id);

    long save(AnswerDto answerDto);

    boolean update(AnswerDto answerDto);

    boolean delete(Long id);

    List<Answer> getByQuestionId(Long id);

    void saveAll(List<Answer> answer);
}
