package com.kopivad.quizzes.repository.jooq;

import com.kopivad.quizzes.domain.Quiz;
import com.kopivad.quizzes.repository.QuizRepository;
import com.kopivad.quizzes.utils.QuizUtils;
import com.kopivad.quizzes.utils.TestUtils;
import org.jooq.DSLContext;
import org.jooq.SQLDialect;
import org.jooq.impl.DSL;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.stream.Collectors;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class QuizRepositoryImplTest {
    private static QuizRepository quizRepository;

    @BeforeAll
    public static void init() {
        DSLContext dslContext = DSL.using(TestUtils.createTestDefaultPgDataSource(), SQLDialect.POSTGRES);
        quizRepository = new QuizRepositoryImpl(dslContext);
    }

    @Test
    public void findAllTest() {
        List<Quiz> generatedQuizzes = QuizUtils.generateQuizzes(5);
        List<Quiz> expected = generatedQuizzes
                .stream()
                .map(quiz -> quiz.toBuilder().id(quizRepository.save(quiz)).build())
                .collect(Collectors.toUnmodifiableList());
        List<Quiz> actual = quizRepository.findAll();

        assertTrue(actual.containsAll(expected));
    }

    @Test
    public void findByIdTest() {
        Quiz generatedQuiz = QuizUtils.generateQuiz();
        long expected = quizRepository.save(generatedQuiz);
        Quiz actual = quizRepository.findById(expected);

        assertThat(actual.getId(), is(expected));
    }

    @Test
    public void saveTest() {
        Quiz generatedQuiz = QuizUtils.generateQuiz();
        long actual = quizRepository.save(generatedQuiz);

        assertThat(actual, notNullValue());
    }

    @Test
    public void updateTest() {
        Quiz generatedQuiz = QuizUtils.generateQuiz();
        Quiz expectedResult = generatedQuiz.toBuilder().id(quizRepository.save(generatedQuiz)).build();
        boolean actual = quizRepository.update(expectedResult);

        assertTrue(actual);
    }

    @Test
    public void deleteTest() {
        Quiz generatedQuiz = QuizUtils.generateQuiz();
        long id = quizRepository.save(generatedQuiz);
        boolean actual = quizRepository.delete(id);

        assertTrue(actual);
    }
}