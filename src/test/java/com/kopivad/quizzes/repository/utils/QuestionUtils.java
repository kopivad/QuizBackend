package com.kopivad.quizzes.repository.utils;

import com.kopivad.quizzes.domain.Question;
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
                .collect(Collectors.toList());
    }

    public static Question generateQuestion() {
        Fairy fairy = Fairy.create();
        TextProducer textProducer = fairy.textProducer();
        return Question
                .builder()
                .title(textProducer.randomString(200))
                .quiz(Quiz.builder().id(6L).build())
                .build();
    }
}
