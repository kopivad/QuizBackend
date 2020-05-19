package com.kopivad.quizzes.service.impl;

import com.kopivad.quizzes.domain.Answer;
import com.kopivad.quizzes.domain.Question;
import com.kopivad.quizzes.domain.Quiz;
import com.kopivad.quizzes.repository.QuizRepository;
import com.kopivad.quizzes.service.AnswerService;
import com.kopivad.quizzes.service.QuestionService;
import com.kopivad.quizzes.service.QuizService;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class QuizServiceImpl implements QuizService {
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
        Quiz quizWithCreationDate = quiz
                .toBuilder()
                .creationDate(LocalDateTime.now())
                .build();

        return quizRepository.save(quizWithCreationDate);
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
        if (ObjectUtils.isNotEmpty(quiz.getQuestions())) {
            List<Question> questions = quiz.getQuestions()
                    .stream()
                    .map(getQuestionWithAnswersFunction(savedQuiz))
                    .collect(Collectors.toUnmodifiableList());

            Quiz quizWithQuestions = savedQuiz.toBuilder().questions(questions).build();
            return quizWithQuestions;
        }

        return savedQuiz;
    }

    private Function<Question, Question> getQuestionWithAnswersFunction(Quiz quiz) {
        return question -> {
            Question questionWithQuiz = question.toBuilder().quiz(quiz).build();
            Question savedQuestion = questionService.save(questionWithQuiz);
            if (ObjectUtils.isNotEmpty(question.getAnswers())) {
                List<Answer> answers = question.getAnswers()
                        .stream()
                        .map(getFullAnswerFunction(savedQuestion))
                        .collect(Collectors.toUnmodifiableList());

                Question questionWithAnswers = savedQuestion.toBuilder().answers(answers).build();
                return questionWithAnswers;
            }
            return savedQuestion;
        };
    }

    private Function<Answer, Answer> getFullAnswerFunction(Question question) {
        return answer -> {
            Answer answerWithQuestion = answer.toBuilder().question(question).build();
            return answerService.save(answerWithQuestion);
        };
    }

    @Override
    public Quiz getFullById(Long id) {
        Quiz quiz = getById(id);
        List<Question> questions = questionService.getByQuizId(id)
                .stream()
                .map(getFullQuestionFunction())
                .collect(Collectors.toUnmodifiableList());

        Quiz quizWithQuestions = quiz.toBuilder().questions(questions).build();
        return quizWithQuestions;
    }

    private Function<Question, Question> getFullQuestionFunction() {
        return question -> {
            List<Answer> answers = answerService.getByQuestionId(question.getId());
            Question questionWithAnswers = question.toBuilder().answers(answers).build();
            return questionWithAnswers;
        };
    }
}


