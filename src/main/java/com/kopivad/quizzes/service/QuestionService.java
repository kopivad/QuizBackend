package com.kopivad.quizzes.service;

import com.kopivad.quizzes.domain.Question;

import java.util.List;

public interface QuestionService {
    List<Question> getAll();

    Question getById(Long id);

    Question save(Question question);

    Question update(Long id, Question question);

    void delete(Long id);

    List<Question> getByQuizId(Long id);

    List<Question> saveAll(List<Question> questions);
}
