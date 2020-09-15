package com.kopivad.quizzes.service.impl;

import com.kopivad.quizzes.domain.Answer;
import com.kopivad.quizzes.dto.AnswerDto;
import com.kopivad.quizzes.repository.AnswerRepository;
import com.kopivad.quizzes.service.AnswerService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static org.apache.commons.lang3.math.NumberUtils.INTEGER_ONE;

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
    public boolean save(AnswerDto dto) {
        int affectedRows = answerRepository.save(dto);
        return affectedRows == INTEGER_ONE;
    }

    @Override
    public boolean update(Answer answer) {
        int affectedRows = answerRepository.update(answer);
        return affectedRows == INTEGER_ONE;
    }

    @Override
    public boolean delete(Long id) {
        int affectedRows = answerRepository.delete(id);
        return affectedRows == INTEGER_ONE;
    }

    @Override
    public List<Answer> getByQuestionId(Long id) {
        return answerRepository.findByQuestionId(id);
    }

    @Override
    public boolean saveAll(List<AnswerDto> answers) {
        int affectedRows = answerRepository.saveAll(answers);
        return affectedRows == answers.size();
    }
}
