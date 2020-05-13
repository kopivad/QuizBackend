package com.kopivad.quizzes.repository.jooq;

import com.kopivad.quizzes.domain.Answer;
import com.kopivad.quizzes.repository.AnswerRepository;
import com.kopivad.quizzes.utils.AnswerUtils;
import com.kopivad.quizzes.utils.TestUtils;
import org.jooq.DSLContext;
import org.jooq.SQLDialect;
import org.jooq.impl.DSL;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.List;
import java.util.stream.Collectors;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;

public class AnswerRepositoryImplTest {
    private static DSLContext dslContext;
    private static AnswerRepository answerRepository;


    @BeforeClass
    public static void init() {
        dslContext = DSL.using(TestUtils.createTestDefaultPgDataSource(), SQLDialect.POSTGRES);
        answerRepository = new AnswerRepositoryImpl(dslContext);
    }

    @Test
    public void findAllTest() {
        List<Answer> generatedAnswers = AnswerUtils.generateAnswers(5);
        List<Answer> savedAnswers = generatedAnswers
                .stream()
                .map(answer -> answerRepository.save(answer))
                .collect(Collectors.toUnmodifiableList());
        List<Answer> allAnswers = answerRepository.findAll();

        assertThat(savedAnswers, notNullValue());
        assertThat(allAnswers, notNullValue());
        assertTrue(allAnswers.containsAll(savedAnswers));
    }

    @Test
    public void findByIdTest() {
        Answer generatedAnswer = AnswerUtils.generateAnswer();
        Answer savedAnswer = answerRepository.save(generatedAnswer);
        Answer answer = answerRepository.findById(savedAnswer.getId());

        assertThat(savedAnswer, notNullValue());
        assertThat(answer, notNullValue());
        assertEquals(savedAnswer, answer);
    }

    @Test
    public void saveTest() {
        Answer generatedAnswer = AnswerUtils.generateAnswer();
        int accountsCountBeforeInsert = answerRepository.findAll().size();
        Answer answer = answerRepository.save(generatedAnswer);
        List<Answer> allAnswers = answerRepository.findAll();

        assertThat(answer.getId(), notNullValue());
        assertThat(accountsCountBeforeInsert + 1, equalTo(allAnswers.size()));
        assertThat(allAnswers, hasItem(answer));
    }

    @Test
    public void updateTest() {
        String dataForUpdate = "Some text";
        Answer generatedAnswer = AnswerUtils.generateAnswer();
        Answer savedAnswer = answerRepository.save(generatedAnswer);
        Answer answerWithText = generatedAnswer.toBuilder().body(dataForUpdate).build();
        Answer updatedUser = answerRepository.update(savedAnswer.getId(), answerWithText);


        assertThat(savedAnswer, notNullValue());
        assertThat(updatedUser, notNullValue());
        assertThat(updatedUser.getId(), equalTo(updatedUser.getId()));
        assertThat(savedAnswer.getBody(), not(equalTo(updatedUser.getBody())));
    }

    @Test
    public void deleteTest() {
        Answer generatedAnswer = AnswerUtils.generateAnswer();
        Answer savedAnswer = answerRepository.save(generatedAnswer);
        List<Answer> allAnswersBeforeDeleting = answerRepository.findAll();
        answerRepository.delete(savedAnswer.getId());
        List<Answer> allAnswersAfterDeleting = answerRepository.findAll();

        assertThat(savedAnswer, notNullValue());
        assertThat(allAnswersBeforeDeleting, notNullValue());
        assertThat(allAnswersAfterDeleting, notNullValue());
        assertThat(allAnswersBeforeDeleting, hasItem(savedAnswer));
        assertThat(allAnswersAfterDeleting, not(hasItem(savedAnswer)));
    }
}