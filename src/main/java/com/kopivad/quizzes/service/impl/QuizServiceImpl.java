package com.kopivad.quizzes.service.impl;

import com.kopivad.quizzes.domain.Quiz;
import com.kopivad.quizzes.dto.EvaluationStepDto;
import com.kopivad.quizzes.dto.FullQuizDto;
import com.kopivad.quizzes.dto.QuestionDto;
import com.kopivad.quizzes.dto.QuizDto;
import com.kopivad.quizzes.repository.QuizRepository;
import com.kopivad.quizzes.service.EvaluationStepService;
import com.kopivad.quizzes.service.QuestionService;
import com.kopivad.quizzes.service.QuizService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.apache.commons.lang3.math.NumberUtils.INTEGER_ONE;

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
        return quiz.map(
                resultQuiz -> new FullQuizDto(
                        resultQuiz,
                        questionService.getFullByQuizId(resultQuiz.getId()),
                        evaluationStepService.getByQuizId(quiz.get().getId())
                ));
    }

    @Override
    public boolean save(QuizDto dto) {
        long quizId = quizRepository.save(dto);
        List<QuestionDto> questions = fillQuizIdForAllQuestionDtos(dto.getQuestions(), quizId);
        List<EvaluationStepDto> steps = fillQuizIdForAllStepDtos(dto.getSteps(), quizId);
        return evaluationStepService.saveAll(steps) && questionService.saveAll(questions);
    }

    private List<EvaluationStepDto> fillQuizIdForAllStepDtos(List<EvaluationStepDto> dtos, long quizId) {
        return dtos
                .stream()
                .map(dto -> new EvaluationStepDto(dto.getMinTotal(), dto.getMaxTotal(), dto.getRating(), quizId))
                .collect(Collectors.toUnmodifiableList());
    }

    private List<QuestionDto> fillQuizIdForAllQuestionDtos(List<QuestionDto> dtos, long quizId) {
        return dtos
                .stream()
                .map(dto -> new QuestionDto(dto.getTitle(), dto.getValue(), dto.getType(), quizId, dto.getAnswers()))
                .collect(Collectors.toUnmodifiableList());
    }

    @Override
    public boolean update(Quiz quiz) {
        int affectedRows = quizRepository.update(quiz);
        return affectedRows == INTEGER_ONE;
    }

    @Override
    public boolean delete(Long id) {
        int affectedRows = quizRepository.delete(id);
        return affectedRows == INTEGER_ONE;
    }

    @Override
    public List<Quiz> getByTitleStartsWith(String title) {
        return quizRepository.findByTitleStartsWith(title);
    }
}


