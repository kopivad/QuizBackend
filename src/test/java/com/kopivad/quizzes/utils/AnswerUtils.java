package com.kopivad.quizzes.utils;

import com.kopivad.quizzes.domain.Answer;
import com.kopivad.quizzes.domain.Question;
import io.codearte.jfairy.Fairy;
import io.codearte.jfairy.producer.text.TextProducer;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class AnswerUtils {
    public static List<Answer> generateAnswers(int size) {
        return IntStream.range(0, size)
                .mapToObj(i -> {
                    Answer answer = generateAnswer()
                            .toBuilder()
                            .id((long) + 1)
                            .build();

                    return answer;
                })
                .collect(Collectors.toUnmodifiableList());
    }

    public static Answer generateAnswer() {
        Fairy fairy = Fairy.create();
        TextProducer textProducer = fairy.textProducer();
        return Answer
                .builder()
                .id(1L)
                .text(textProducer.randomString(200))
                .isRight(true)
                .question(Question.builder().id(111L).build())
                .build();
    }
}
