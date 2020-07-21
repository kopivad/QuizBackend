package com.kopivad.quizzes.utils;

import com.kopivad.quizzes.domain.EvaluationStep;
import com.kopivad.quizzes.domain.Question;
import com.kopivad.quizzes.domain.Quiz;
import com.kopivad.quizzes.dto.EvaluationStepDto;
import com.kopivad.quizzes.dto.QuestionDto;
import com.kopivad.quizzes.dto.QuizDto;
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

    public static Quiz generateQuizWithFullData() {
        Quiz quiz = generateQuiz();
        int size = 10;
        List<Question> questions = QuestionUtils.generateQuestions(size);
        List<EvaluationStep> steps = EvaluationStepUtils.generateSteps(size);
        return quiz.toBuilder().questions(questions).evaluationSteps(steps).build();
    }

    public static QuizDto generateQuizDto() {
        Fairy fairy = Fairy.create();
        TextProducer textProducer = fairy.textProducer();
        int charsCount = 200;
        int defaultTotal = 100;
        return QuizDto
                .builder()
                .id(LONG_ONE)
                .title(textProducer.randomString(charsCount))
                .description(textProducer.randomString(charsCount))
                .total(defaultTotal)
                .active(true)
                .creationDate(LocalDateTime.now())
                .authorId(UserUtils.generateUser().getId())
                .build();
    }

    public static QuizDto generateQuizDtoWithFullData() {
        QuizDto dto = generateQuizDto();
        int questionsCount = 10;
        int stepsCount = 10;
        List<QuestionDto> questionDtos = QuestionUtils.generateQuestionDtosWithAnswers(questionsCount);
        List<EvaluationStepDto> stepDtos = EvaluationStepUtils.generateStepDtos(stepsCount);
        return dto.toBuilder().questions(questionDtos).evaluationSteps(stepDtos).build();
    }
}
