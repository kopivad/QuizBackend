package com.kopivad.quizzes.service.impl;

import com.kopivad.quizzes.domain.Answer;
import com.kopivad.quizzes.domain.Question;
import com.kopivad.quizzes.domain.QuizAnswer;
import com.kopivad.quizzes.dto.QuizAnswerDto;
import com.kopivad.quizzes.mapper.QuizAnswerMapper;
import com.kopivad.quizzes.repository.QuizAnswerRepository;
import com.kopivad.quizzes.service.AnswerService;
import com.kopivad.quizzes.service.QuestionService;
import com.kopivad.quizzes.service.QuizAnswerService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class QuizAnswerServiceImpl implements QuizAnswerService {
    private final QuizAnswerRepository quizAnswerRepository;
    private final QuizAnswerMapper mapper;
    private final QuestionService questionService;
    private final AnswerService answerService;

    @Override
    public long save(QuizAnswerDto quizAnswerDto) {
        QuizAnswer quizAnswer = mapper.toEntity(quizAnswerDto);
        return quizAnswerRepository.save(quizAnswer);
    }

    @Override
    public List<QuizAnswer> findAllBySessionId(long sessionId) {
        List<QuizAnswer> quizAnswers = quizAnswerRepository.findAllBySessionId(sessionId);
        return quizAnswers.stream()
                .map(a -> {
                    Question question = questionService.getById(a.getQuestion().getId());
                    Answer answer = answerService.getById(a.getAnswer().getId());
                    return a.toBuilder().question(question).answer(answer).build();
                }).collect(Collectors.toUnmodifiableList());
    }
}
