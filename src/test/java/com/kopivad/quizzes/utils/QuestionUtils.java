package com.kopivad.quizzes.utils;

import com.kopivad.quizzes.domain.Question;
import com.kopivad.quizzes.domain.QuestionType;
import io.codearte.jfairy.Fairy;
import io.codearte.jfairy.producer.text.TextProducer;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.apache.commons.lang3.math.NumberUtils.INTEGER_ZERO;
import static org.apache.commons.lang3.math.NumberUtils.LONG_ONE;

public class QuestionUtils {
    public static List<Question> generateQuestions(int size) {
        return IntStream.range(INTEGER_ZERO, size)
                .mapToObj(i -> generateQuestion()
                        .toBuilder()
                        .id(i + LONG_ONE)
                        .build())
                .collect(Collectors.toUnmodifiableList());
    }

    public static Question generateQuestion() {
        Fairy fairy = Fairy.create();
        TextProducer textProducer = fairy.textProducer();
        int charsCount = 200;
        int defaultValue = 15;

        return Question
                .builder()
                .id(LONG_ONE)
                .type(QuestionType.SINGLE)
                .title(textProducer.randomString(charsCount))
                .quiz(QuizUtils.generateQuiz())
                .value(defaultValue)
                .build();
    }

    public static Question generateQuestionWithAnswers() {
        Fairy fairy = Fairy.create();
        TextProducer textProducer = fairy.textProducer();
        int charsCount = 200;
        int defaultValue = 15;

        Question question = Question
                .builder()
                .type(QuestionType.SINGLE)
                .title(textProducer.randomString(charsCount))
                .value(defaultValue)
                .quiz(QuizUtils.generateQuiz())
                .build();
        int size = 10;
        return question.toBuilder().answers(AnswerUtils.generateAnswers(size)).build();
    }
}
