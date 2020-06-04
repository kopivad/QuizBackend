package com.kopivad.quizzes.service;

import com.kopivad.quizzes.domain.Question;
import com.kopivad.quizzes.form.QuestionForm;

import java.util.List;

public interface QuestionService {
    List<Question> getAll();

    Question getById(Long id);

    Question save(QuestionForm questionForm);

    Question update(Long id, QuestionForm questionForm);

    void delete(Long id);

    List<Question> getByQuizId(Long id);

    List<Question> saveAll(List<Question> questions);
}
