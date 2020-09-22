package com.kopivad.quizzes.repository.jooq;

import com.kopivad.quizzes.domain.Quiz;
import com.kopivad.quizzes.dto.QuizDto;
import com.kopivad.quizzes.repository.QuizRepository;
import com.kopivad.quizzes.utils.QuizUtils;
import com.kopivad.quizzes.utils.TestUtils;
import com.kopivad.quizzes.utils.UserUtils;
import org.jooq.DSLContext;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;

public class QuizRepositoryImplTest {
    private final DSLContext dslContext = TestUtils.createTestDefaultDSLContext();
    private final QuizRepository quizRepository = new QuizRepositoryImpl(dslContext);

    @BeforeAll
    static void init() {
        UserUtils.insertDefaultUser();
    }

    @BeforeEach
    void setUp() {
        QuizUtils.deleteAll();
    }

    @Test
    void findAllTest() {
        int expected = 10;
        QuizUtils.insertRandomQuizzes(expected);
        List<Quiz> actual = quizRepository.findAll();

        assertThat(actual.size(), is(expected));
    }

    @Test
    void findByIdTest() {
        long id = QuizUtils.insertRandomUserInDb();
        Optional<Quiz> actual = quizRepository.findById(id);

        assertThat(actual.isPresent(), is(true));
    }

    @Test
    void saveTest() {
        QuizDto dto = QuizUtils.generateQuizDto();
        long actual = quizRepository.save(dto);

        assertThat(actual, notNullValue());
    }

    @Test
    void updateTest() {
        long id = QuizUtils.insertRandomUserInDb();
        Quiz quiz = QuizUtils.generateQuiz(id);
        int expected = 1;
        int actual = quizRepository.update(quiz);

        assertThat(actual, is(expected));
    }

    @Test
    void deleteTest() {
        long id = QuizUtils.insertRandomUserInDb();
        int expected = 1;
        int actual = quizRepository.delete(id);

        assertThat(actual, is(expected));
    }

    @Test
    void findByTitleStartsWithTest() {
        String prefix = "test";
        int expected = 10;
        QuizUtils.insertRandomQuizzesWithSimilarTitle(prefix, expected);
        List<Quiz> actual = quizRepository.findByTitleStartsWith(prefix);

        assertThat(actual.size(), is(expected));
    }
}