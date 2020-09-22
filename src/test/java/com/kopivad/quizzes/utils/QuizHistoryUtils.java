package com.kopivad.quizzes.utils;

import com.kopivad.quizzes.domain.QuizHistory;
import com.kopivad.quizzes.domain.db.tables.records.QuizHistoriesRecord;
import com.kopivad.quizzes.dto.QuizHistoryDto;
import org.jooq.DSLContext;
import org.jooq.InsertReturningStep;

import java.util.List;
import java.util.Random;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static com.kopivad.quizzes.domain.db.tables.QuizHistories.QUIZ_HISTORIES;
import static org.apache.commons.lang3.math.NumberUtils.INTEGER_ZERO;
import static org.apache.commons.lang3.math.NumberUtils.LONG_ONE;

public class QuizHistoryUtils {
    private final static DSLContext DSL_CONTEXT = TestUtils.createTestDefaultDSLContext();
    public final static Long TEST_HISTORY_ID = 1L;
    public final static String TEST_PDF_FILE_NAME = "TEST_PDF_FILE.pdf";
    public final static String TEST_CSV_FILE_NAME = "TEST_CSV_FILE.csv";
    public final static Random RANDOM = new Random();

    public static QuizHistory generateHistory(long id) {
        return new QuizHistory(
                id,
                RANDOM.nextInt(),
                UUID.randomUUID().toString(),
                QuizSessionUtils.TEST_SESSION_ID,
                UserUtils.TEST_USER_ID,
                UUID.randomUUID().toString(),
                UUID.randomUUID().toString()
        );
    }

    public static QuizHistoryDto generateHistoryDto() {
        return new QuizHistoryDto(
                RANDOM.nextInt(),
                UUID.randomUUID().toString(),
                QuizSessionUtils.TEST_SESSION_ID,
                UserUtils.TEST_USER_ID,
                TEST_PDF_FILE_NAME,
                TEST_CSV_FILE_NAME
        );
    }

    public static List<QuizHistoryDto> generateHistoryDtos(int size) {
        return IntStream
                .range(INTEGER_ZERO, size)
                .mapToObj(i -> generateHistoryDto())
                .collect(Collectors.toUnmodifiableList());
    }

    public static long insertRandomHistory() {
        QuizHistoryDto dto = generateHistoryDto();
        return DSL_CONTEXT
                .insertInto(QUIZ_HISTORIES, QUIZ_HISTORIES.ID, QUIZ_HISTORIES.TOTAL, QUIZ_HISTORIES.RATING, QUIZ_HISTORIES.CSV_FILE_NAME, QUIZ_HISTORIES.PDF_FILE_NAME, QUIZ_HISTORIES.SESSION_ID, QUIZ_HISTORIES.USER_ID)
                .values(TEST_HISTORY_ID, dto.getTotal(), dto.getRating(), dto.getCsvFilename(), dto.getPdfFilename(), dto.getSessionId(), dto.getUserId())
                .returning(QUIZ_HISTORIES.ID)
                .execute();
    }

    public static void insertRandomHistories(int size) {
        List<QuizHistoryDto> dtos = generateHistoryDtos(size);
        List<InsertReturningStep<QuizHistoriesRecord>> values = dtos
                .stream()
                .map(dto -> DSL_CONTEXT
                    .insertInto(QUIZ_HISTORIES, QUIZ_HISTORIES.TOTAL, QUIZ_HISTORIES.RATING, QUIZ_HISTORIES.CSV_FILE_NAME, QUIZ_HISTORIES.PDF_FILE_NAME, QUIZ_HISTORIES.SESSION_ID, QUIZ_HISTORIES.USER_ID)
                    .values(dto.getTotal(), dto.getRating(), dto.getCsvFilename(), dto.getPdfFilename(), dto.getSessionId(), dto.getUserId()))
                .collect(Collectors.toUnmodifiableList());
        DSL_CONTEXT.batch(values).execute();
    }

    public static void deleteAll() {
        DSL_CONTEXT.deleteFrom(QUIZ_HISTORIES).execute();
    }

    public static QuizHistory generateHistory() {
        return new QuizHistory(
                TEST_HISTORY_ID,
                RANDOM.nextInt(),
                UUID.randomUUID().toString(),
                QuizSessionUtils.TEST_SESSION_ID,
                UserUtils.TEST_USER_ID,
                TEST_PDF_FILE_NAME,
                TEST_CSV_FILE_NAME
        );
    }

    public static List<QuizHistory> generateHistories(int size) {
        return IntStream
                .range(INTEGER_ZERO, size)
                .mapToObj(i -> generateHistory(i + LONG_ONE))
                .collect(Collectors.toUnmodifiableList());
    }
}
