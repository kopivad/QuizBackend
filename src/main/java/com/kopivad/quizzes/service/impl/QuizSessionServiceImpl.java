package com.kopivad.quizzes.service.impl;

import com.kopivad.quizzes.domain.QuizSession;
import com.kopivad.quizzes.dto.QuizSessionDto;
import com.kopivad.quizzes.repository.QuizSessionRepository;
import com.kopivad.quizzes.service.QuizSessionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class QuizSessionServiceImpl implements QuizSessionService {
    private final QuizSessionRepository quizSessionRepository;

    @Override
    public long save(QuizSessionDto dto) {
        return quizSessionRepository.save(dto);
    }

    @Override
    public Optional<QuizSession> getById(long sessionId) {
        return quizSessionRepository.findById(sessionId);
    }
}
