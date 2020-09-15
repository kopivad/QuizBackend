package com.kopivad.quizzes.service.impl;

import com.kopivad.quizzes.domain.Answer;
import com.kopivad.quizzes.dto.SaveAnswerDto;
import com.kopivad.quizzes.repository.AnswerRepository;
import com.kopivad.quizzes.service.AnswerService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AnswerServiceImpl implements AnswerService {
    private final AnswerRepository answerRepository;

    @Override
    public List<Answer> getAll() {
        return answerRepository.findAll();
    }

    @Override
    public Optional<Answer> getById(Long id) {
        return answerRepository.findById(id);
    }

    @Override
    public boolean save(SaveAnswerDto dto) {
        Answer answer = new Answer(1L, dto.getBody(), dto.isRight(), dto.getQuestionId());
        return answerRepository.save(answer);
    }

    @Override
    public boolean update(Answer answer) {
        return answerRepository.update(answer);
    }

    @Override
    public boolean delete(Long id) {
        return answerRepository.delete(id);
    }

    @Override
    public List<Answer> getByQuestionId(Long id) {
        return answerRepository.findByQuestionId(id);
    }

    @Override
    public boolean saveAll(List<Answer> answers) {
        answers.forEach(answerRepository::save);
        return false;  // Looking for better solution, will better to check if all was saved
    }
}
