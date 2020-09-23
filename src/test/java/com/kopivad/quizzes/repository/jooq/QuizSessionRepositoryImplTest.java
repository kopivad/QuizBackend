package com.kopivad.quizzes.repository.jooq;

import com.kopivad.quizzes.domain.QuizSession;
import com.kopivad.quizzes.dto.QuizSessionDto;
import com.kopivad.quizzes.repository.QuizSessionRepository;
import com.kopivad.quizzes.utils.QuizSessionUtils;
import com.kopivad.quizzes.utils.QuizUtils;
import com.kopivad.quizzes.utils.TestUtils;
import com.kopivad.quizzes.utils.UserUtils;
import org.jooq.DSLContext;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;

public class QuizSessionRepositoryImplTest {
    private final DSLContext dslContext = TestUtils.createTestDefaultDSLContext();
    private final QuizSessionRepository quizSessionRepository = new QuizSessionRepositoryImpl(dslContext);

    @BeforeAll
    private static void insertData() {
        UserUtils.insertDefaultUser();
        QuizUtils.insertDefaultQuiz();
    }

    @AfterAll
    private static void deleteData() {
        UserUtils.deleteDefaultUser();
        QuizUtils.deleteDefaultQuiz();
    }

    @AfterEach
    private void deleteQuizSessions() {
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