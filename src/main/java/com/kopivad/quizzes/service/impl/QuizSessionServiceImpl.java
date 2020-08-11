package com.kopivad.quizzes.service.impl;

import com.kopivad.quizzes.domain.QuizSession;
import com.kopivad.quizzes.dto.QuizAnswerDto;
import com.kopivad.quizzes.dto.QuizDto;
import com.kopivad.quizzes.dto.QuizSessionDto;
import com.kopivad.quizzes.mapper.QuizSessionMapper;
import com.kopivad.quizzes.repository.QuizSessionRepository;
import com.kopivad.quizzes.service.QuizAnswerService;
import com.kopivad.quizzes.service.QuizService;
import com.kopivad.quizzes.service.QuizSessionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class QuizSessionServiceImpl implements QuizSessionService {
    private final QuizSessionRepository quizSessionRepository;
    private final QuizSessionMapper quizSessionMapper;
    private final QuizAnswerService quizAnswerService;

    @Override
    public long startSession(QuizSessionDto quizSessionDto) {
        QuizSession session = quizSessionMapper.toEntity(quizSessionDto);
        return quizSessionRepository.save(session);
    }

    @Override
    public QuizSessionDto getById(long sessionId) {
        QuizSession session = quizSessionRepository.findById(sessionId);
        QuizSessionDto dto = quizSessionMapper.toDto(session);
        List<QuizAnswerDto> quizAnswers = quizAnswerService.getAllBySessionId(sessionId);
        return dto.toBuilder().results(quizAnswers).build();
    }
}
