package com.kopivad.quizzes.utils;

import com.kopivad.quizzes.domain.Answer;
import com.kopivad.quizzes.domain.Question;
import com.kopivad.quizzes.domain.QuestionType;
import com.kopivad.quizzes.dto.AnswerDto;
import com.kopivad.quizzes.dto.QuestionDto;
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

    public static Question generateQuestionWithFullData() {
        Question question = generateQuestion();
        int size = 4;
        List<Answer> answers = AnswerUtils.generateAnswers(size);
        return question.toBuilder().answers(answers).build();
    }

    public static List<QuestionDto> generateQuestionDtosWithAnswers(int size) {
        return IntStream
                .range(INTEGER_ZERO, size)
                .mapToObj(i -> {
                            int answerCount = 4;
                            return generateQuestionDto()
                                    .toBuilder()
                                    .id(i + LONG_ONE)
                                    .answers(AnswerUtils.generateAnswerDtos(answerCount))
                                    .build();
                        }
                )
                .collect(Collectors.toUnmodifiableList());
    }

    public static QuestionDto generateQuestionDto() {
        Fairy fairy = Fairy.create();
        TextProducer textProducer = fairy.textProducer();
        int charsCount = 200;
        int defaultValue = 15;

        return QuestionDto
                .builder()
                .id(LONG_ONE)
                .type(QuestionType.SINGLE)
                .title(textProducer.randomString(charsCount))
                .quizId(QuizUtils.generateQuiz().getId())
                .value(defaultValue)
                .build();
    }

    public static QuestionDto generateQuestionDtoWithFullData() {
        int size = 4;
        List<AnswerDto> answerDtos = AnswerUtils.generateAnswerDtos(size);
        return generateQuestionDto().toBuilder().answers(answerDtos).build();
    }

    public static List<QuestionDto> generateQuestionDtos(int size) {
        return IntStream
                .range(INTEGER_ZERO, size)
                .mapToObj(i -> generateQuestionDto().toBuilder().id(i + LONG_ONE).build())
                .collect(Collectors.toUnmodifiableList());
    }
}
