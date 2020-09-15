package com.kopivad.quizzes.service.impl;

import com.kopivad.quizzes.domain.QuizAnswer;
import com.kopivad.quizzes.dto.QuizAnswerDto;
import com.kopivad.quizzes.repository.QuizAnswerRepository;
import com.kopivad.quizzes.service.QuizAnswerService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

import static org.apache.commons.lang3.math.NumberUtils.INTEGER_ONE;


@Service
@RequiredArgsConstructor
public class QuizAnswerServiceImpl implements QuizAnswerService {
    private final QuizAnswerRepository quizAnswerRepository;

    @Override
    public boolean save(QuizAnswerDto dto) {
        int affectedRows = quizAnswerRepository.save(dto);
        return affectedRows == INTEGER_ONE;
    }

    @Override
    public List<QuizAnswer> getAllBySessionId(long sessionId) {
        return quizAnswerRepository.findAllBySessionId(sessionId);
    }
}
