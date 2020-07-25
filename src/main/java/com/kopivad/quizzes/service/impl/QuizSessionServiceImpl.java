package com.kopivad.quizzes.service.impl;

import com.kopivad.quizzes.domain.Quiz;
import com.kopivad.quizzes.domain.QuizAnswer;
import com.kopivad.quizzes.domain.QuizSession;
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
    private final QuizSessionMapper mapper;
    private final QuizAnswerService quizAnswerService;
    private final QuizService quizService;

    @Override
    public long startSession(QuizSessionDto quizSessionDto) {
        QuizSession session = mapper.toEntity(quizSessionDto);
        return quizSessionRepository.save(session);
    }

    @Override
    public QuizSession getById(long sessionId) {
        QuizSession session = quizSessionRepository.findById(sessionId);
        Quiz quiz = quizService.getById(session.getQuiz().getId());
        List<QuizAnswer> quizAnswers = quizAnswerService.getAllBySessionId(sessionId);
        return session.toBuilder().results(quizAnswers).quiz(quiz).build();
    }
}
