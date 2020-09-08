package com.kopivad.quizzes.repository.jooq;

import com.kopivad.quizzes.domain.Question;
import com.kopivad.quizzes.repository.QuestionRepository;
import com.kopivad.quizzes.utils.QuestionUtils;
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

public class QuestionRepositoryImplTest {
    private static QuestionRepository questionRepository;

    @BeforeAll
    public static void init() {
        DSLContext dslContext = DSL.using(TestUtils.createTestDefaultPgDataSource(), SQLDialect.POSTGRES);
        questionRepository = new QuestionRepositoryImpl(dslContext);
    }

    @Test
    public void findAllTest() {
        List<Question> generatedQuestions = QuestionUtils.generateQuestions(5);
        List<Question> expected = generatedQuestions
                .stream()
                .map(q -> q.toBuilder().id(questionRepository.save(q)).build())
                .collect(Collectors.toUnmodifiableList());
        List<Question> actual = questionRepository.findAll();

        assertTrue(actual.containsAll(expected));
    }

    @Test
    public void findByIdTest() {
        Question question = QuestionUtils.generateQuestion();
        long id = questionRepository.save(question);
        Question actual = questionRepository.findById(id);

        assertThat(actual.getId(), is(id));
    }

    @Test
    public void saveTest() {
        Question question = QuestionUtils.generateQuestion();
        long id = questionRepository.save(question);

        assertThat(id, notNullValue());
    }

    @Test
    public void updateTest() {
        Question question = QuestionUtils.generateQuestion();
        Question expectedResult = question.toBuilder().id(questionRepository.save(question)).build();
        boolean actual = questionRepository.update(expectedResult);

        assertTrue(actual);
    }

    @Test
    public void deleteTest() {
        Question question = QuestionUtils.generateQuestion();
        long id = questionRepository.save(question);
        boolean actual = questionRepository.delete(id);

        assertTrue(actual);
    }
}