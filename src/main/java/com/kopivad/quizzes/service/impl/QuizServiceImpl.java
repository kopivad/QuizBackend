package com.kopivad.quizzes.service.impl;

import com.kopivad.quizzes.domain.Answer;
import com.kopivad.quizzes.domain.Question;
import com.kopivad.quizzes.domain.Quiz;
import com.kopivad.quizzes.repository.QuizRepository;
import com.kopivad.quizzes.service.AnswerService;
import com.kopivad.quizzes.service.QuestionService;
import com.kopivad.quizzes.service.QuizService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class QuizServiceImpl implements QuizService {
    @Qualifier("jooqQuizRepository")
    private final QuizRepository quizRepository;
    private final QuestionService questionService;
    private final AnswerService answerService;

    @Override
    public List<Quiz> getAll() {
        return quizRepository.findAll();
    }

    @Override
    public Quiz getById(Long id) {
        return quizRepository.findById(id);
    }

    @Override
    public Quiz save(Quiz quiz) {
        quiz.setCreationDate(LocalDateTime.now());
        return quizRepository.save(quiz);
    }

    @Override
    public Quiz update(Long id, Quiz quiz) {
        return quizRepository.update(id, quiz);
    }

    @Override
    public void delete(Long id) {
        quizRepository.delete(id);
    }

    @Override
    public Quiz saveFull(Quiz quiz) {
        Quiz savedQuiz = save(quiz);
        List<Question> questions = quiz.getQuestions()
                .stream()
                .map(question -> {
                    question.setQuiz(savedQuiz);
                    Question savedQuestion = questionService.save(question);
                    List<Answer> answers = question.getAnswers()
                            .stream()
                            .map(answer -> {
                                answer.setQuestion(savedQuestion);
                                return answerService.save(answer);
                            })
                            .collect(Collectors.toList());
                    savedQuestion.setAnswers(answers);
                    return savedQuestion;
                }).collect(Collectors.toList());
        savedQuiz.setQuestions(questions);
        return savedQuiz;
    }
}


