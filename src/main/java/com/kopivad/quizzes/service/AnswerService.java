package com.kopivad.quizzes.service;

import com.kopivad.quizzes.domain.Answer;

import java.util.List;

public interface AnswerService {
    List<Answer> getAll();

    Answer getById(Long id);

    Answer save(Answer answer);

    Answer update(Long id, Answer answer);

    void delete(Long id);
}
