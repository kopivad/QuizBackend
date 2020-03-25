package com.kopivad.testingsystem.service;

import com.kopivad.testingsystem.domain.Question;

import java.util.List;

public interface QuestionService {

    List<Question> getAll();

    Question getById(Long id);

    Question save(Question question);

    Question update(Long id, Question question);

    void delete(Long id);
}
