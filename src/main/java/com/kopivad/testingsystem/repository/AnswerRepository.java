package com.kopivad.testingsystem.repository;

import com.kopivad.testingsystem.domain.Answer;

import java.util.List;

public interface AnswerRepository {
    List<Answer> findAll();

    Answer findById(Long id);

    Answer save(Answer answer);

    Answer update(Long id, Answer answer);

    void delete(Long id);
}
