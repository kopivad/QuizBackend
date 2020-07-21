package com.kopivad.quizzes.utils;

import com.kopivad.quizzes.domain.QuizHistory;
import com.kopivad.quizzes.dto.QuizHistoryDto;
import io.codearte.jfairy.Fairy;
import io.codearte.jfairy.producer.text.TextProducer;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.apache.commons.lang3.math.NumberUtils.*;

public class QuizHistoryUtils {
    public static QuizHistory generateHistory() {
        Fairy fairy = Fairy.create();
        TextProducer producer = fairy.textProducer();
        int total = 100;
        int charsCount = 100;
        return QuizHistory
                .builder()
                .id(LONG_ONE)
                .session(QuizSessionUtils.generateQuizSession())
                .user(UserUtils.generateUser())
                .total(total)
                .rating(producer.word(INTEGER_ONE))
                .csvFilename(producer.randomString(charsCount))
                .pdfFilename(producer.randomString(charsCount))
                .build();
    }

    public static List<QuizHistory> generateHistories(int size) {
        return IntStream
                .range(INTEGER_ZERO, size)
                .mapToObj(i -> generateHistory().toBuilder().id(LONG_ONE + i).build())
                .collect(Collectors.toUnmodifiableList());
    }

    public static QuizHistoryDto generateHistoryDto() {
        Fairy fairy = Fairy.create();
        TextProducer producer = fairy.textProducer();
        int total = 100;
        int charsCount = 100;
        return QuizHistoryDto
                .builder()
                .id(LONG_ONE)
                .sessionId(QuizSessionUtils.generateQuizSession().getId())
                .userId(UserUtils.generateUser().getId())
                .total(total)
                .rating(producer.word(INTEGER_ONE))
                .csvFilename(producer.randomString(charsCount))
                .pdfFilename(producer.randomString(charsCount))
                .build();
    }

    public static List<QuizHistoryDto> generateHistoryDtos(int stepsCount) {
        return IntStream
                .range(INTEGER_ZERO, stepsCount)
                .mapToObj(i -> generateHistoryDto().toBuilder().id(LONG_ONE + i).build())
                .collect(Collectors.toUnmodifiableList());
    }
}
