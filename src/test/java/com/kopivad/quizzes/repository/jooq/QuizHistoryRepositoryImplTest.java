package com.kopivad.quizzes.repository.jooq;

import com.kopivad.quizzes.domain.QuizHistory;
import com.kopivad.quizzes.dto.QuizHistoryDto;
import com.kopivad.quizzes.repository.QuizHistoryRepository;
import com.kopivad.quizzes.utils.*;
import org.jooq.DSLContext;
import org.junit.jupiter.api.*;

import java.util.List;
import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;

public class QuizHistoryRepositoryImplTest {
    private final DSLContext dslContext = TestUtils.createTestDefaultDSLContext();
    private final QuizHistoryRepository quizHistoryRepository = new QuizHistoryRepositoryImpl(dslContext);

    @BeforeAll
    private static void insertData() {
        UserUtils.insertDefaultUser();
        QuizUtils.insertDefaultQuiz();
        QuizSessionUtils.insertDefaultQuizSession();
    }

    @AfterAll
    private static void deleteData() {
        UserUtils.deleteDefaultUser();
        QuizUtils.deleteDefaultQuiz();
        QuizSessionUtils.deleteDefaultQuizSession();
    }

    @AfterEach
    private void deleteQuizHistories() {
        QuizHistoryUtils.deleteAll();
    }

    @Test
    void saveTest() {
        QuizHistoryDto history = QuizHistoryUtils.generateHistoryDto();
        long actual = quizHistoryRepository.save(history);

        assertThat(actual, notNullValue());
    }

    @Test
    void findByIdTest() {
        long id = QuizHistoryUtils.insertRandomHistory();
        Optional<QuizHistory> actual = quizHistoryRepository.findById(id);

        assertThat(actual.isPresent(), is(true));
    }

    @Test
    void findAllTest() {
        int expected = 10;
        QuizHistoryUtils.insertRandomHistories(expected);
        List<QuizHistory> actual = quizHistoryRepository.findAll();

        assertThat(actual.size(), is(expected));
    }
}