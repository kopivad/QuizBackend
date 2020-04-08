package com.kopivad.quizzes.repository.utils;

import com.kopivad.quizzes.domain.Quiz;
import com.kopivad.quizzes.domain.User;
import io.codearte.jfairy.Fairy;
import io.codearte.jfairy.producer.text.TextProducer;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class QuizUtils {
    public static List<Quiz> generateQuizzes(int size) {
        return IntStream.range(0, size)
                .mapToObj(i -> generateQuiz())
                .collect(Collectors.toList());
    }

    public static Quiz generateQuiz() {
        Fairy fairy = Fairy.create();
        TextProducer textProducer = fairy.textProducer();
        return Quiz
                .builder()
                .title(textProducer.loremIpsum())
                .description(textProducer.loremIpsum())
                .active(true)
                .author(User.builder().id(50L).build())
                .build();
    }
}
