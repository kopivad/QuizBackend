package com.kopivad.quizzes.repository.jooq;

import com.kopivad.quizzes.domain.QuizAnswer;
import com.kopivad.quizzes.domain.QuizSession;
import com.kopivad.quizzes.repository.QuizAnswerRepository;
import com.kopivad.quizzes.repository.QuizSessionRepository;
import com.kopivad.quizzes.utils.QuizAnswerUtils;
import com.kopivad.quizzes.utils.QuizSessionUtils;
import com.kopivad.quizzes.utils.TestUtils;
import org.jooq.DSLContext;
import org.jooq.SQLDialect;
import org.jooq.impl.DSL;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.List;
import java.util.stream.Collectors;

import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

public class QuizAnswerRepositoryImplTest {
    private static QuizAnswerRepository quizAnswerRepository;
    private static QuizSessionRepository quizSessionRepository;

    @BeforeClass
    public static void init() {
        DSLContext dslContext = DSL.using(TestUtils.createTestDefaultPgDataSource(), SQLDialect.POSTGRES);
        quizAnswerRepository = new QuizAnswerRepositoryImpl(dslContext);
        quizSessionRepository = new QuizSessionRepositoryImpl(dslContext);
    }

    @Test
    public void saveTest() {
        QuizAnswer quizAnswer = QuizAnswerUtils.generateAnswer();
        long actual = quizAnswerRepository.save(quizAnswer);

        assertThat(actual, notNullValue());
    }

    @Test
    public void findAllBySessionIdTest() {
        QuizSession session = QuizSessionUtils.generateQuizSession();
        long sessionId = quizSessionRepository.save(session);
        int size = 5;
        List<QuizAnswer> expected = QuizAnswerUtils.generateAnswersWithSessionId(sessionId, size)
                .stream()
                .map(a -> a.toBuilder().id(quizAnswerRepository.save(a)).build())
                .collect(Collectors.toUnmodifiableList());

        List<QuizAnswer> actual = quizAnswerRepository.findAllBySessionId(sessionId);

        assertTrue(actual.containsAll(expected));
    }
}