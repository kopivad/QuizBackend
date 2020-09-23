package com.kopivad.quizzes.repository.jooq;

import com.kopivad.quizzes.domain.QuizAnswer;
import com.kopivad.quizzes.dto.QuizAnswerDto;
import com.kopivad.quizzes.repository.QuizAnswerRepository;
import com.kopivad.quizzes.utils.*;
import org.jooq.DSLContext;
import org.junit.jupiter.api.*;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class QuizAnswerRepositoryImplTest {
    private final DSLContext dslContext = TestUtils.createTestDefaultDSLContext();
    private final QuizAnswerRepository quizAnswerRepository = new QuizAnswerRepositoryImpl(dslContext);

    @BeforeAll
    private static void insertData() {
        UserUtils.insertDefaultUser();
        QuizUtils.insertDefaultQuiz();
        QuestionUtils.insertDefaultQuestion();
        AnswerUtils.insertDefaultAnswerInDb();
        QuizSessionUtils.insertDefaultQuizSession();
    }

    @AfterAll
    private static void deleteData() {
        UserUtils.deleteDefaultUser();
        QuizUtils.deleteDefaultQuiz();
        QuestionUtils.deleteDefaultQuestion();
        AnswerUtils.deleteDefaultAnswer();
        QuizSessionUtils.deleteDefaultQuizSession();
    }

    @AfterEach
    private void deleteQuizAnswers() {
        QuizAnswerUtils.deleteAll();
    }

    @Test
    void saveTest() {
        QuizAnswerDto dto = QuizAnswerUtils.generateAnswerDto();
        int expected = 1;
        int actual = quizAnswerRepository.save(dto);

        assertThat(actual, is(expected));
    }

    @Test
    void findAllBySessionIdTest() {
        int expected = 10;
        QuizAnswerUtils.insertRandomQuizAnswer(expected);
        List<QuizAnswer> actual = quizAnswerRepository.findAllBySessionId(QuizSessionUtils.TEST_SESSION_ID);

        assertThat(actual.size(), is(expected));
    }
}