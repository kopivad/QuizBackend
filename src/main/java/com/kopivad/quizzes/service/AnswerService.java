package com.kopivad.quizzes.service;

import com.kopivad.quizzes.domain.Answer;
import com.kopivad.quizzes.form.AnswerForm;

import java.util.List;

public interface AnswerService {
    List<Answer> getAll();

    Answer getById(Long id);

    Answer save(AnswerForm answerForm);

    Answer update(Long id, AnswerForm answerForm);

    void delete(Long id);

    List<Answer> getByQuestionId(Long id);

    List<Answer> saveAll(List<Answer> answers);
}
