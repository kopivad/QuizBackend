package com.kopivad.quizzes.repository.jooq;

import com.kopivad.quizzes.domain.QuizHistory;
import com.kopivad.quizzes.repository.QuizHistoryRepository;
import com.kopivad.quizzes.utils.QuizHistoryUtils;
import com.kopivad.quizzes.utils.TestUtils;
import org.jooq.DSLContext;
import org.jooq.SQLDialect;
import org.jooq.impl.DSL;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.List;
import java.util.stream.Collectors;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

public class QuizHistoryRepositoryImplTest {
    private static QuizHistoryRepository quizHistoryRepository;

    @BeforeClass
    public static void init() {
        DSLContext dslContext = DSL.using(TestUtils.createTestDefaultPgDataSource(), SQLDialect.POSTGRES);
        quizHistoryRepository = new QuizHistoryRepositoryImpl(dslContext);
    }

    @Test
    public void saveTest() {
        QuizHistory history = QuizHistoryUtils.generateHistory();
        long actual = quizHistoryRepository.save(history);

        assertThat(actual, notNullValue());
    }

    @Test
    public void findByIdTest() {
        QuizHistory history = QuizHistoryUtils.generateHistory();
        long expected = quizHistoryRepository.save(history);
        QuizHistory actual = quizHistoryRepository.findById(expected);

        assertThat(actual.getId(), is(expected));
    }

    @Test
    public void findAllTest() {
        List<QuizHistory> histories = QuizHistoryUtils.generateHistories(5);
        List<QuizHistory> expected = histories
                .stream()
                .map(h -> h.toBuilder().id(quizHistoryRepository.save(h)).build())
                .collect(Collectors.toUnmodifiableList());
        List<QuizHistory> actual = quizHistoryRepository.findAll();

        assertTrue(actual.containsAll(expected));
    }
}