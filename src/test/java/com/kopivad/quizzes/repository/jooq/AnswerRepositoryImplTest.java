package com.kopivad.quizzes.repository.jooq;

import com.kopivad.quizzes.domain.Answer;
import com.kopivad.quizzes.repository.AnswerRepository;
import com.kopivad.quizzes.utils.AnswerUtils;
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

public class AnswerRepositoryImplTest {
    private static AnswerRepository answerRepository;


    @BeforeAll
    public static void init() {
        DSLContext dslContext = DSL.using(TestUtils.createTestDefaultPgDataSource(), SQLDialect.POSTGRES);
        answerRepository = new AnswerRepositoryImpl(dslContext);
    }

    @Test
    public void findAllTest() {
        List<Answer> generatedAnswers = AnswerUtils.generateAnswers(5);
        List<Answer> expected = generatedAnswers
                .stream()
                .map(answer -> answer.toBuilder().id(answerRepository.save(answer)).build())
                .collect(Collectors.toUnmodifiableList());
        List<Answer> actual = answerRepository.findAll();

        assertTrue(actual.containsAll(expected));
    }

    @Test
    public void findByIdTest() {
        Answer generatedAnswer = AnswerUtils.generateAnswer();
        long expected = answerRepository.save(generatedAnswer);
        Answer actual = answerRepository.findById(expected);

        assertThat(actual.getId(), is(expected));
    }

    @Test
    public void saveTest() {
        Answer generatedAnswer = AnswerUtils.generateAnswer();
        long actual = answerRepository.save(generatedAnswer);

        assertThat(actual, notNullValue());
    }

    @Test
    public void updateTest() {
        Answer generatedAnswer = AnswerUtils.generateAnswer();
        Answer savedAnswer = generatedAnswer.toBuilder().id(answerRepository.save(generatedAnswer)).build();
        boolean actual = answerRepository.update(savedAnswer);

        assertTrue(actual);
    }

    @Test
    public void deleteTest() {
        Answer generatedAnswer = AnswerUtils.generateAnswer();
        long id = answerRepository.save(generatedAnswer);
        boolean actual = answerRepository.delete(id);

        assertTrue(actual);
    }
}