package com.kopivad.quizzes.service.impl;

import com.kopivad.quizzes.domain.Answer;
import com.kopivad.quizzes.dto.AnswerDto;
import com.kopivad.quizzes.mapper.AnswerMapper;
import com.kopivad.quizzes.repository.AnswerRepository;
import com.kopivad.quizzes.service.AnswerService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AnswerServiceImpl implements AnswerService {
    private final AnswerRepository answerRepository;
    private final AnswerMapper answerMapper;

    @Override
    public List<AnswerDto> getAll() {
        List<Answer> answers = answerRepository.findAll();
        return answers
                .stream()
                .map(answerMapper::toDto)
                .collect(Collectors.toUnmodifiableList());
    }

    @Override
    public Answer getById(Long id) {
        return answerRepository.findById(id);
    }

    @Override
    public long save(AnswerDto answerDto) {
        Answer answer = answerMapper.toEntity(answerDto);
        return answerRepository.save(answer);
    }

    @Override
    public boolean update(AnswerDto answerDto) {
        Answer answer = answerMapper.toEntity(answerDto);
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
    public void saveAll(List<Answer> answers) {
        answers.forEach(answerRepository::save);
    }
}
