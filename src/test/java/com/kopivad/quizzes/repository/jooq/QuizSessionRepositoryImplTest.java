package com.kopivad.quizzes.repository.jooq;

import com.kopivad.quizzes.domain.QuizSession;
import com.kopivad.quizzes.repository.QuizSessionRepository;
import com.kopivad.quizzes.utils.QuizSessionUtils;
import com.kopivad.quizzes.utils.TestUtils;
import org.jooq.DSLContext;
import org.jooq.SQLDialect;
import org.jooq.impl.DSL;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

public class QuizSessionRepositoryImplTest {
    private static QuizSessionRepository quizSessionRepository;

    @BeforeClass
    public static void init() {
        DSLContext dslContext = DSL.using(TestUtils.createTestDefaultPgDataSource(), SQLDialect.POSTGRES);
        quizSessionRepository = new QuizSessionRepositoryImpl(dslContext);
    }

    @Test
    public void saveTest() {
        QuizSession session= QuizSessionUtils.generateQuizSession();
        long actual = quizSessionRepository.save(session);

        assertThat(actual, notNullValue());
    }

    @Test
    public void updateTest() {
        QuizSession session= QuizSessionUtils.generateQuizSession();
        QuizSession expected = session.toBuilder().id(quizSessionRepository.save(session)).build();
        boolean actual = quizSessionRepository.update(expected);

        assertTrue(actual);
    }

    @Test
    public void findByIdTest() {
        QuizSession session= QuizSessionUtils.generateQuizSession();
        long expected = quizSessionRepository.save(session);
        QuizSession actual = quizSessionRepository.findById(expected);

        assertThat(actual.getId(), is(expected));
    }
}