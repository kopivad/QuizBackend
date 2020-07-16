package com.kopivad.quizzes.utils;

import com.kopivad.quizzes.domain.Question;
import com.kopivad.quizzes.domain.Quiz;
import io.codearte.jfairy.Fairy;
import io.codearte.jfairy.producer.text.TextProducer;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.apache.commons.lang3.math.NumberUtils.INTEGER_ZERO;
import static org.apache.commons.lang3.math.NumberUtils.LONG_ONE;

public class QuizUtils {
    public static List<Quiz> generateQuizzes(int size) {
        return IntStream.range(INTEGER_ZERO, size)
                .mapToObj(i -> generateQuiz()
                        .toBuilder()
                        .id(i + LONG_ONE)
                        .build())
                .collect(Collectors.toUnmodifiableList());
    }

    public static Quiz generateQuiz() {
        Fairy fairy = Fairy.create();
        TextProducer textProducer = fairy.textProducer();
        int charsCount = 200;
        int defaultTotal = 100;
        return Quiz
                .builder()
                .id(LONG_ONE)
                .title(textProducer.randomString(charsCount))
                .description(textProducer.randomString(charsCount))
                .total(defaultTotal)
                .active(true)
                .creationDate(LocalDateTime.now())
                .author(UserUtils.generateUser())
                .build();
    }

    public static Quiz generateQuizWithQuestions() {
        Fairy fairy = Fairy.create();
        TextProducer textProducer = fairy.textProducer();
        int charsCount = 200;
        int defaultTotal = 100;
        Quiz quiz = Quiz
                .builder()
                .title(textProducer.randomString(charsCount))
                .description(textProducer.randomString(charsCount))
                .total(defaultTotal)
                .active(true)
                .author(UserUtils.generateUser())
                .build();

        int size = 10;
        List<Question> questions = QuestionUtils.generateQuestions(size);
        return quiz.toBuilder().questions(questions).build();
    }
}
