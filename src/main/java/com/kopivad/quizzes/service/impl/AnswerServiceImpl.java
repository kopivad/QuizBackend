package com.kopivad.quizzes.service.impl;

import com.kopivad.quizzes.domain.Answer;
import com.kopivad.quizzes.form.AnswerForm;
import com.kopivad.quizzes.repository.AnswerRepository;
import com.kopivad.quizzes.service.AnswerService;
import com.kopivad.quizzes.utils.FormUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AnswerServiceImpl implements AnswerService {
    private final AnswerRepository answerRepository;

    @Override
    public List<Answer> getAll() {
        return answerRepository.findAll();
    }

    @Override
    public Answer getById(Long id) {
        return answerRepository.findById(id);
    }

    @Override
    public Answer save(AnswerForm answerForm) {
        Answer answer = FormUtils.toAnswer(answerForm);
        return answerRepository.save(answer);
    }

    @Override
    public Answer update(Long id, AnswerForm answerForm) {
        Answer answer = FormUtils.toAnswer(answerForm);
        return answerRepository.update(id, answer);
    }

    @Override
    public void delete(Long id) {
        answerRepository.delete(id);
    }

    @Override
    public List<Answer> getByQuestionId(Long id) {
        return answerRepository.findByQuestionId(id);
    }

    @Override
    public List<Answer> saveAll(List<Answer> answers) {
        return answers
                .stream()
                .map(answerRepository::save)
                .collect(Collectors.toUnmodifiableList());
    }
}
