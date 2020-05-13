package com.kopivad.quizzes.utils;

import com.kopivad.quizzes.domain.Question;
import com.kopivad.quizzes.domain.Quiz;
import com.kopivad.quizzes.domain.User;
import io.codearte.jfairy.Fairy;
import io.codearte.jfairy.producer.text.TextProducer;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class QuizUtils {
    public static List<Quiz> generateQuizzes(int size) {
        return IntStream.range(0, size)
                .mapToObj(i -> {
                    Quiz quiz = generateQuiz()
                            .toBuilder()
                            .id((long) + i)
                            .build();

                    return quiz;
                })
                .collect(Collectors.toUnmodifiableList());
    }

    public static Quiz generateQuiz() {
        Fairy fairy = Fairy.create();
        TextProducer textProducer = fairy.textProducer();
        return Quiz
                .builder()
                .id(1L)
                .title(textProducer.randomString(200))
                .description(textProducer.randomString(200))
                .active(true)
                .creationDate(LocalDateTime.now())
                .author(User.builder().id(1L).build())
                .build();
    }

    public static Quiz generateFullQuiz() {
        Quiz quiz = generateQuiz();
        List<Question> questions = QuestionUtils.generateQuestions(10)
                .stream()
                .map(question -> {
                    Question questionWithAnswers = question
                            .toBuilder()
                            .answers(AnswerUtils.generateAnswers(4))
                            .build();

                    return questionWithAnswers;
                })
                .collect(Collectors.toUnmodifiableList());
        Quiz quizWithQuestions = quiz.toBuilder().questions(questions).build();
        return quizWithQuestions;
    }
}
