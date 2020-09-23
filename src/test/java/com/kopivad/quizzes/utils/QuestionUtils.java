package com.kopivad.quizzes.utils;

import com.kopivad.quizzes.domain.Question;
import com.kopivad.quizzes.domain.QuestionType;
import com.kopivad.quizzes.domain.db.tables.records.QuestionsRecord;
import com.kopivad.quizzes.dto.FullQuestionDto;
import com.kopivad.quizzes.dto.QuestionDto;
import org.jooq.DSLContext;
import org.jooq.InsertReturningStep;

import java.util.List;
import java.util.Random;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static com.kopivad.quizzes.domain.db.tables.Questions.QUESTIONS;
import static org.apache.commons.lang3.math.NumberUtils.INTEGER_ZERO;

public class QuestionUtils {
    private final static DSLContext DSL_CONTEXT = TestUtils.createTestDefaultDSLContext();
    public final static Long TEST_QUESTION_ID = 1L;
    private final static Random RANDOM = new Random();

    public static List<Question> generateQuestions(int size) {
        return IntStream.range(INTEGER_ZERO, size)
                .mapToObj(QuestionUtils::generateQuestion)
                .collect(Collectors.toUnmodifiableList());
    }

    public static Question generateQuestion() {
        return generateQuestion(TEST_QUESTION_ID);
    }

    public static QuestionDto generateQuestionDto() {
        return new QuestionDto(
                UUID.randomUUID().toString(),
                RANDOM.nextInt(100),
                QuestionType.SINGLE,
                QuizUtils.TEST_QUIZ_ID,
                AnswerUtils.generateAnswerDtos(4)
        );
    }

    public static Question generateQuestion(long id) {
        return new Question(
                id,
                UUID.randomUUID().toString(),
                RANDOM.nextInt(100),
                QuestionType.SINGLE,
                QuizUtils.TEST_QUIZ_ID
        );
    }

    public static List<QuestionDto> generateQuestionDtos(int size) {
        QuestionDto dto = generateQuestionDto();
        return IntStream
                .range(INTEGER_ZERO, size)
                .mapToObj(i -> new QuestionDto(dto.getTitle(), dto.getValue(), dto.getType(), dto.getQuizId(), dto.getAnswers()))
                .collect(Collectors.toUnmodifiableList());
    }

    public static long insertRandomQuestionInDb() {
        QuestionDto dto = generateQuestionDto();
        return DSL_CONTEXT
                .insertInto(QUESTIONS)
                .set(QUESTIONS.TITLE, dto.getTitle())
                .set(QUESTIONS.VALUE, dto.getValue())
                .set(QUESTIONS.TYPE, dto.getType().name())
                .set(QUESTIONS.QUIZ_ID, QuizUtils.TEST_QUIZ_ID)
                .returning(QUESTIONS.ID)
                .fetchOne()
                .getId();
    }

    public static void insertRandomQuestionsInDb(int size) {
        List<QuestionDto> dtos = generateQuestionDtos(size);
        List<InsertReturningStep<QuestionsRecord>> values = dtos.stream().map(dto -> DSL_CONTEXT
                .insertInto(QUESTIONS, QUESTIONS.TITLE, QUESTIONS.VALUE, QUESTIONS.TYPE, QUESTIONS.QUIZ_ID)
                .values(dto.getTitle(), dto.getValue(), dto.getType().name(), dto.getQuizId()))
                .collect(Collectors.toUnmodifiableList());

        DSL_CONTEXT.batch(values).execute();
    }

    public static void deleteAll() {
        DSL_CONTEXT.deleteFrom(QUESTIONS).execute();
    }

    public static void insertDefaultQuestion() {
        QuestionDto dto = generateQuestionDto();
        DSL_CONTEXT
                .insertInto(QUESTIONS)
                .set(QUESTIONS.ID, TEST_QUESTION_ID)
                .set(QUESTIONS.TITLE, dto.getTitle())
                .set(QUESTIONS.VALUE, dto.getValue())
                .set(QUESTIONS.TYPE, dto.getType().name())
                .set(QUESTIONS.QUIZ_ID, QuizUtils.TEST_QUIZ_ID)
                .execute();
    }

    public static List<FullQuestionDto> generateFullQuestionDtos(int size) {
        return IntStream
                .range(INTEGER_ZERO, size)
                .mapToObj(i -> generateFullQuestionDto())
                .collect(Collectors.toUnmodifiableList());
    }

    public static FullQuestionDto generateFullQuestionDto() {
        return new FullQuestionDto(generateQuestion(), AnswerUtils.generateAnswers(10));
    }

    public static void deleteDefaultQuestion() {
        DSL_CONTEXT.deleteFrom(QUESTIONS).where(QUESTIONS.ID.eq(TEST_QUESTION_ID)).execute();
    }
}
