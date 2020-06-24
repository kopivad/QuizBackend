package com.kopivad.quizzes.service.impl;

import com.kopivad.quizzes.domain.EvaluationStep;
import com.kopivad.quizzes.domain.Question;
import com.kopivad.quizzes.domain.Quiz;
import com.kopivad.quizzes.domain.db.tables.EvaluationSteps;
import com.kopivad.quizzes.repository.QuizRepository;
import com.kopivad.quizzes.service.EvaluationStepService;
import com.kopivad.quizzes.service.QuestionService;
import com.kopivad.quizzes.service.QuizService;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
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
    public Quiz getById(Long id) {
        Quiz quizById = quizRepository.findById(id);
        List<Question> questions = questionService.getByQuizId(id);
        return quizById.toBuilder().questions(questions).build();
    }

    @Override
    public Quiz save(Quiz quiz) {
        Quiz quizWithCreationDate = quiz.toBuilder().creationDate(LocalDateTime.now()).build();
        Quiz savedQuiz = quizRepository.save(quizWithCreationDate);
        if (ObjectUtils.isNotEmpty(quiz.getEvaluationSteps())) {
            List<EvaluationStep> steps = setQuizForAllSteps(savedQuiz, quiz.getEvaluationSteps());
            List<EvaluationStep> savedSteps = evaluationStepService.saveAll(steps);
            savedQuiz = savedQuiz.toBuilder().evaluationSteps(savedSteps).build();
        }
        if (ObjectUtils.isNotEmpty(quiz.getQuestions())) {
            List<Question> questions = setQuizForAllQuestions(savedQuiz, quiz.getQuestions());
            List<Question> savedQuestions = questionService.saveAll(questions);
            return savedQuiz.toBuilder().questions(savedQuestions).build();
        }
        return savedQuiz;
    }

    @Override
    public Quiz update(Long id, Quiz quiz) {
        return quizRepository.update(id, quiz);
    }

    @Override
    public void delete(Long id) {
        quizRepository.delete(id);
    }

    private List<Question> setQuizForAllQuestions(Quiz quiz, List<Question> questions) {
        return questions
                .stream()
                .map(question ->  question.toBuilder().quiz(quiz).build())
                .collect(Collectors.toUnmodifiableList());
    }

    private List<EvaluationStep> setQuizForAllSteps(Quiz quiz, List<EvaluationStep> steps) {
        return steps
                .stream()
                .map(s -> s.toBuilder().quiz(quiz).build())
                .collect(Collectors.toUnmodifiableList());
    }
}


