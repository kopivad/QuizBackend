package com.kopivad.quizzes.service.impl;

import com.kopivad.quizzes.domain.Quiz;
import com.kopivad.quizzes.repository.QuizRepository;
import com.kopivad.quizzes.service.QuizService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class QuizServiceImpl implements QuizService {
    @Qualifier("jooqQuizRepository")
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
        quiz.setCreationDate(LocalDateTime.now());
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


