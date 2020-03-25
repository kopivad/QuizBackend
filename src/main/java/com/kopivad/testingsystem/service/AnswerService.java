package com.kopivad.testingsystem.service;

import com.kopivad.testingsystem.domain.Answer;

import java.util.List;

public interface AnswerService {
    List<Answer> getAll();

    Answer getById(Long id);

    Answer save(Answer answer);

    Answer update(Long id, Answer answer);

    void delete(Long id);
}
