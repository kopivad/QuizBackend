package com.kopivad.quizzes.service.impl;

import com.kopivad.quizzes.domain.QuizAnswer;
import com.kopivad.quizzes.domain.QuizHistory;
import com.kopivad.quizzes.domain.QuizSession;
import com.kopivad.quizzes.dto.FullQuizSessionDto;
import com.kopivad.quizzes.dto.SaveQuizSessionDto;
import com.kopivad.quizzes.repository.QuizSessionRepository;
import com.kopivad.quizzes.service.QuizAnswerService;
import com.kopivad.quizzes.service.QuizHistoryService;
import com.kopivad.quizzes.service.QuizSessionService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Lazy))
public class QuizSessionServiceImpl implements QuizSessionService {
    private final QuizSessionRepository quizSessionRepository;
    private final QuizAnswerService quizAnswerService;
    private final QuizHistoryService quizHistoryService;

    @Override
    public long save(SaveQuizSessionDto dto) {
        QuizSession quizSession = new QuizSession(1L, dto.getQuizId(), dto.getUserId(), LocalDateTime.now());
        return quizSessionRepository.save(quizSession);
    }

    @Override
    public Optional<FullQuizSessionDto> getFullById(long sessionId) {
        Optional<QuizSession> session = quizSessionRepository.findById(sessionId);
        if (session.isPresent()) {
            List<QuizAnswer> quizAnswers = quizAnswerService.getAllBySessionId(sessionId);
            List<QuizHistory> quizHistories = quizHistoryService.getAllBySessionId(sessionId);
            FullQuizSessionDto fullQuizSessionDto = new FullQuizSessionDto(session.get(), quizAnswers, quizHistories);
            return Optional.of(fullQuizSessionDto);
        }
        return Optional.empty();
    }

    @Override
    public Optional<QuizSession> getById(long sessionId) {
        return quizSessionRepository.findById(sessionId);
    }
}
