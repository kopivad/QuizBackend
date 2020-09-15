package com.kopivad.quizzes.service.impl;

import com.kopivad.quizzes.domain.EvaluationStep;
import com.kopivad.quizzes.domain.Quiz;
import com.kopivad.quizzes.dto.*;
import com.kopivad.quizzes.repository.QuizRepository;
import com.kopivad.quizzes.service.EvaluationStepService;
import com.kopivad.quizzes.service.QuestionService;
import com.kopivad.quizzes.service.QuizService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class QuizServiceImpl implements QuizService {
    private final QuizRepository quizRepository;
    private final QuestionService questionService;
    private final EvaluationStepService evaluationStepService;

    @Override
    public List<Quiz> getAll() {
        return quizRepository.findAll();
    }

    @Override
    public Optional<FullQuizDto> getById(Long id) {
        Optional<Quiz> quiz = quizRepository.findById(id);
        if (quiz.isPresent()) {
            List<FullQuestionDto> questions = questionService.getFullByQuizId(quiz.get().getId());
            List<EvaluationStep> steps = evaluationStepService.getByQuizId(quiz.get().getId());
            FullQuizDto fullQuizDto = new FullQuizDto(quiz.get(), questions, steps);
            return Optional.of(fullQuizDto);
        }
        return Optional.empty();
    }

    @Override
    public boolean save(SaveQuizDto dto) {
        Quiz quiz = new Quiz(1L, dto.getTitle(), dto.getDescription(), dto.getTotal(), true, LocalDateTime.now(), dto.getAuthorId());
        long quizId = quizRepository.save(quiz);
        List<SaveQuestionDto> questions = fillQuizIdForAllQuestionDtos(dto.getQuestions(), quizId);
        List<SaveEvaluationStepDto> steps = fillQuizIdForAllStepDtos(dto.getSteps(), quizId);

        return evaluationStepService.saveAll(steps) && questionService.saveAll(questions);
    }

    private List<SaveEvaluationStepDto> fillQuizIdForAllStepDtos(List<SaveEvaluationStepDto> dto, long quizId) {
        return dto
                .stream()
                .map(d -> new SaveEvaluationStepDto(d.getMinTotal(), d.getMaxTotal(), d.getRating(), quizId))
                .collect(Collectors.toUnmodifiableList());
    }

    private List<SaveQuestionDto> fillQuizIdForAllQuestionDtos(List<SaveQuestionDto> dto, long quizId) {
        return dto
                .stream()
                .map(d -> new SaveQuestionDto(d.getTitle(), d.getValue(), d.getType(), quizId, d.getAnswers()))
                .collect(Collectors.toUnmodifiableList());
    }

    @Override
    public boolean update(Quiz quiz) {
        return quizRepository.update(quiz);
    }

    @Override
    public boolean delete(Long id) {
        return quizRepository.delete(id);
    }

    @Override
    public List<Quiz> getByGroupId(long id) {
        return quizRepository.findByGroupId(id);
    }

    @Override
    public List<Quiz> getByTitleStartsWith(String title) {
        return quizRepository.findByTitleStartsWith(title);
    }
}


