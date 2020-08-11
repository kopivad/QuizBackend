package com.kopivad.quizzes.service.impl;

import com.kopivad.quizzes.domain.QuizAnswer;
import com.kopivad.quizzes.dto.QuizAnswerDto;
import com.kopivad.quizzes.mapper.QuizAnswerMapper;
import com.kopivad.quizzes.repository.QuizAnswerRepository;
import com.kopivad.quizzes.service.QuizAnswerService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class QuizAnswerServiceImpl implements QuizAnswerService {
    private final QuizAnswerRepository quizAnswerRepository;
    private final QuizAnswerMapper quizAnswerMapper;

    @Override
    public long save(QuizAnswerDto quizAnswerDto) {
        QuizAnswer quizAnswer = quizAnswerMapper.toEntity(quizAnswerDto);
        return quizAnswerRepository.save(quizAnswer);
    }

    @Override
    public List<QuizAnswerDto> getAllBySessionId(long sessionId) {
        List<QuizAnswer> quizAnswers = quizAnswerRepository.findAllBySessionId(sessionId);
        return quizAnswers.stream().map(quizAnswerMapper::toDto).collect(Collectors.toUnmodifiableList());
    }
}
