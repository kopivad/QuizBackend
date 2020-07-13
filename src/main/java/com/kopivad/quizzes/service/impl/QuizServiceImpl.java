package com.kopivad.quizzes.service.impl;

import com.kopivad.quizzes.domain.EvaluationStep;
import com.kopivad.quizzes.domain.Question;
import com.kopivad.quizzes.domain.Quiz;
import com.kopivad.quizzes.dto.EvaluationStepDto;
import com.kopivad.quizzes.dto.QuestionDto;
import com.kopivad.quizzes.dto.QuizDto;
import com.kopivad.quizzes.mapper.QuizMapper;
import com.kopivad.quizzes.repository.QuizRepository;
import com.kopivad.quizzes.service.EvaluationStepService;
import com.kopivad.quizzes.service.QuestionService;
import com.kopivad.quizzes.service.QuizService;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class QuizServiceImpl implements QuizService {
    private final QuizRepository quizRepository;
    private final QuestionService questionService;
    private final EvaluationStepService evaluationStepService;
    private final QuizMapper mapper;

    @Override
    public List<QuizDto> getAll() {
        List<Quiz> quizzes = quizRepository.findAll();
        return quizzes
                .stream()
                .map(mapper::toDto)
                .collect(Collectors.toUnmodifiableList());
    }

    @Override
    public Quiz getById(Long id) {
        Quiz quizById = quizRepository.findById(id);
        List<Question> questions = questionService.getByQuizId(id);
        List<EvaluationStep> steps = evaluationStepService.getByQuizId(id);
        return quizById.toBuilder().questions(questions).evaluationSteps(steps).build();
    }

    @Override
    public long save(QuizDto quizDto) {
        Quiz quiz = mapper.toEntity(quizDto);
        long id = quizRepository.save(quiz);
        if (ObjectUtils.isNotEmpty(quizDto.getEvaluationSteps())) {
            List<EvaluationStepDto> steps = setQuizForAllSteps(id, quizDto.getEvaluationSteps());
            evaluationStepService.saveAll(steps);
        }
        if (ObjectUtils.isNotEmpty(quizDto.getQuestions())) {
            List<QuestionDto> dtos = setQuizForAllQuestions(id, quizDto.getQuestions());
            questionService.saveAll(dtos);
        }
        return id;
    }

    @Override
    public boolean update(QuizDto quizDto) {
        Quiz quiz = mapper.toEntity(quizDto);
        return quizRepository.update(quiz);
    }

    @Override
    public boolean delete(Long id) {
        return quizRepository.delete(id);
    }

    private List<QuestionDto> setQuizForAllQuestions(long quizId, List<QuestionDto> dtos) {
        return dtos
                .stream()
                .map(question ->  question.toBuilder().quizId(quizId).build())
                .collect(Collectors.toUnmodifiableList());
    }

    private List<EvaluationStepDto> setQuizForAllSteps(long quizId, List<EvaluationStepDto> dtos) {
        return dtos
                .stream()
                .map(step -> step.toBuilder().quizId(quizId).build())
                .collect(Collectors.toUnmodifiableList());
    }
}


