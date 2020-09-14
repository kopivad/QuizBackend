package com.kopivad.quizzes.service.impl;

import com.kopivad.quizzes.domain.QuizAnswer;
import com.kopivad.quizzes.dto.SaveQuizAnswerDto;
import com.kopivad.quizzes.repository.QuizAnswerRepository;
import com.kopivad.quizzes.service.QuizAnswerService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@RequiredArgsConstructor
public class QuizAnswerServiceImpl implements QuizAnswerService {
    private final QuizAnswerRepository quizAnswerRepository;

    @Override
    public boolean save(SaveQuizAnswerDto dto) {
        QuizAnswer quizAnswer = new QuizAnswer(1L, dto.getQuestionId(), dto.getSessionId(), dto.getAnswerId());
        return quizAnswerRepository.save(quizAnswer);
    }

    @Override
    public List<QuizAnswer> getAllBySessionId(long sessionId) {
        return quizAnswerRepository.findAllBySessionId(sessionId);
    }
}
