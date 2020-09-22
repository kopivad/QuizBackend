package com.kopivad.quizzes.repository.jooq;

import com.kopivad.quizzes.domain.QuizSession;
import com.kopivad.quizzes.dto.QuizSessionDto;
import com.kopivad.quizzes.repository.QuizSessionRepository;
import com.kopivad.quizzes.utils.QuizSessionUtils;
import com.kopivad.quizzes.utils.QuizUtils;
import com.kopivad.quizzes.utils.TestUtils;
import org.jooq.DSLContext;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;

public class QuizSessionRepositoryImplTest {
    private final DSLContext dslContext = TestUtils.createTestDefaultDSLContext();
    private final QuizSessionRepository quizSessionRepository = new QuizSessionRepositoryImpl(dslContext);

    @BeforeAll
    static void init() {
        QuizUtils.insertDefaultQuiz();
    }

    @BeforeEach
    void setUp() {
        QuizSessionUtils.deleteAll();
    }

    @Test
    public void saveTest() {
        QuizSessionDto session= QuizSessionUtils.generateQuizSessionDto();
        long actual = quizSessionRepository.save(session);

        assertThat(actual, notNullValue());
    }

    @Test
    public void updateTest() {
        long id = QuizSessionUtils.insertRandomQuizSession();
        QuizSession quizSession = QuizSessionUtils.generateQuizSession(id);
        int expected = 1;
        int actual = quizSessionRepository.update(quizSession);

        assertThat(actual, is(expected));
    }

    @Test
    public void findByIdTest() {
        long id = QuizSessionUtils.insertRandomQuizSession();

        Optional<QuizSession> actual = quizSessionRepository.findById(id);

        assertThat(actual.isPresent(), is(true));
    }
}