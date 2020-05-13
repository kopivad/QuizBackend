package com.kopivad.quizzes.utils;

import com.kopivad.quizzes.domain.Question;
import com.kopivad.quizzes.domain.QuestionType;
import com.kopivad.quizzes.domain.Quiz;
import io.codearte.jfairy.Fairy;
import io.codearte.jfairy.producer.text.TextProducer;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class QuestionUtils {
    public static List<Question> generateQuestions(int size) {
        return IntStream.range(0, size)
                .mapToObj(i -> generateQuestion())
                .collect(Collectors.toUnmodifiableList());
    }

    public static Question generateQuestion() {
        Fairy fairy = Fairy.create();
        TextProducer textProducer = fairy.textProducer();
        return Question
                .builder()
                .type(QuestionType.SINGLE)
                .title(textProducer.randomString(200))
                .quiz(Quiz.builder().id(75L).build())
                .build();
    }
}
