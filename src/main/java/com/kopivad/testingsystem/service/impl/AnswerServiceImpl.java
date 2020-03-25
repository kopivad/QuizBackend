package com.kopivad.testingsystem.service.impl;

import com.kopivad.testingsystem.domain.Answer;
import com.kopivad.testingsystem.repository.AnswerRepository;
import com.kopivad.testingsystem.service.AnswerService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
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
    public Answer save(Answer answer) {
        return answerRepository.save(answer);
    }

    @Override
    public Answer update(Long id, Answer answer) {
        return answerRepository.update(id, answer);
    }

    @Override
    public void delete(Long id) {
        answerRepository.delete(id);
    }
}
