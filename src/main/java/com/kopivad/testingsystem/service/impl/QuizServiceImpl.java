package com.kopivad.testingsystem.service.impl;

import com.kopivad.testingsystem.domain.Quiz;
import com.kopivad.testingsystem.repository.QuizRepository;
import com.kopivad.testingsystem.service.QuizService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@AllArgsConstructor
@Service
public class QuizServiceImpl implements QuizService {
    private final QuizRepository quizRepository;

    @Override
    public List<Quiz> getAll() {
        return quizRepository.findAll();
    }

    @Override
    public Quiz getById(Long id) {
        return quizRepository.findById(id);
    }

    @Override
    public Quiz save(Quiz quiz) {
        return quizRepository.save(quiz);
    }

    @Override
    public Quiz update(Long id, Quiz quiz) {
        return quizRepository.update(id, quiz);
    }

    @Override
    public void delete(Long id) {
        quizRepository.delete(id);
    }
}
