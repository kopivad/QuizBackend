package com.kopivad.quizzes.repository.jooq;

import com.kopivad.quizzes.domain.QuizHistory;
import com.kopivad.quizzes.dto.QuizHistoryDto;
import com.kopivad.quizzes.repository.QuizHistoryRepository;
import com.kopivad.quizzes.utils.QuizHistoryUtils;
import com.kopivad.quizzes.utils.QuizSessionUtils;
import com.kopivad.quizzes.utils.QuizUtils;
import com.kopivad.quizzes.utils.TestUtils;
import org.jooq.DSLContext;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;

public class QuizHistoryRepositoryImplTest {
    private final DSLContext dslContext = TestUtils.createTestDefaultDSLContext();
    private final QuizHistoryRepository quizHistoryRepository = new QuizHistoryRepositoryImpl(dslContext);

    @BeforeAll
    static void init() {
        QuizUtils.insertDefaultQuiz();
        QuizSessionUtils.insertDefaultQuizSession();
    }

    @BeforeEach
    void setUp() {
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