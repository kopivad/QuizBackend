package com.kopivad.quizzes.utils;

import com.kopivad.quizzes.domain.Answer;
import com.kopivad.quizzes.domain.Question;
import com.kopivad.quizzes.form.AnswerForm;
import io.codearte.jfairy.Fairy;
import io.codearte.jfairy.producer.text.TextProducer;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.apache.commons.lang3.math.NumberUtils.INTEGER_ZERO;
import static org.apache.commons.lang3.math.NumberUtils.LONG_ONE;

public class AnswerUtils {
    public static List<Answer> generateAnswers(int size) {
        return IntStream.range(INTEGER_ZERO, size)
                .mapToObj(i -> {
                    Answer answer = generateAnswer()
                            .toBuilder()
                            .id(i + LONG_ONE)
                            .build();

                    return answer;
                })
                .collect(Collectors.toUnmodifiableList());
    }

    public static Answer generateAnswer() {
        Fairy fairy = Fairy.create();
        TextProducer textProducer = fairy.textProducer();
        int charsCount = 200;
        return Answer
                .builder()
                .id(LONG_ONE)
                .body(textProducer.randomString(charsCount))
                .isRight(true)
                .question(Question.builder().id(LONG_ONE).build())
                .build();
    }

    public static AnswerForm generateAnswerForm() {
        Fairy fairy = Fairy.create();
        TextProducer textProducer = fairy.textProducer();
        int charsCount = 200;
        return AnswerForm
                .builder()
                .body(textProducer.randomString(charsCount))
                .right(true)
                .questionId(LONG_ONE)
                .build();
    }

    public static List<AnswerForm> generateAnswerForms(int size) {
        return IntStream.range(INTEGER_ZERO, size)
                .mapToObj(i -> generateAnswerForm())
                .collect(Collectors.toUnmodifiableList());
    }
}
