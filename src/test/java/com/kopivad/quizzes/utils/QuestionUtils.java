package com.kopivad.quizzes.utils;

import com.kopivad.quizzes.domain.Question;
import com.kopivad.quizzes.domain.QuestionType;
import com.kopivad.quizzes.domain.Quiz;
import com.kopivad.quizzes.form.QuestionForm;
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
                .mapToObj(i -> generateQuestion())
                .collect(Collectors.toUnmodifiableList());
    }

    public static Question generateQuestion() {
        Fairy fairy = Fairy.create();
        TextProducer textProducer = fairy.textProducer();
        int charsCount = 200;
        return Question
                .builder()
                .id(LONG_ONE)
                .type(QuestionType.SINGLE)
                .title(textProducer.randomString(charsCount))
                .quiz(Quiz.builder().id(LONG_ONE).build())
                .build();
    }

    public static List<QuestionForm> generateQuestionForms(int size) {
        return IntStream.range(INTEGER_ZERO, size)
                .mapToObj(i -> generateQuestionForm())
                .collect(Collectors.toUnmodifiableList());
    }

    public static QuestionForm generateQuestionForm() {
        Fairy fairy = Fairy.create();
        TextProducer textProducer = fairy.textProducer();
        int charsCount = 200;
        return QuestionForm
                .builder()
                .type(QuestionType.SINGLE)
                .title(textProducer.randomString(charsCount))
                .quizId(LONG_ONE)
                .build();
    }

    public static QuestionForm generateQuestionFormWithAnswers() {
        Fairy fairy = Fairy.create();
        TextProducer textProducer = fairy.textProducer();
        int charsCount = 200;
        QuestionForm questionForm = QuestionForm
                .builder()
                .type(QuestionType.SINGLE)
                .title(textProducer.randomString(charsCount))
                .quizId(LONG_ONE)
                .build();
        int size = 10;
        return questionForm.toBuilder().answers(AnswerUtils.generateAnswerForms(size)).build();
    }
}
