package com.kopivad.quizzes.repository.jooq;

import com.kopivad.quizzes.domain.Question;
import com.kopivad.quizzes.repository.QuestionRepository;
import com.kopivad.quizzes.repository.utils.QuestionUtils;
import com.kopivad.quizzes.repository.utils.TestUtils;
import org.jooq.DSLContext;
import org.jooq.SQLDialect;
import org.jooq.impl.DSL;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.List;
import java.util.stream.Collectors;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

public class QuestionRepositoryImplTest {
    private static DSLContext dslContext;
    private static QuestionRepository questionRepository;


    @BeforeClass
    public static void init() {
        dslContext = DSL.using(TestUtils.createTestDefaultPgDataSource(), SQLDialect.POSTGRES);
        questionRepository = new QuestionRepositoryImpl(dslContext);
    }

    @Test
    public void findAllTest() {
        List<Question> generateQuestions = QuestionUtils.generateQuestions(5);
        List<Question> savedQuestions = generateQuestions
                .stream()
                .map(question -> questionRepository.save(question))
                .collect(Collectors.toList());
        List<Question> allQuestions = questionRepository.findAll();

        assertThat(savedQuestions, notNullValue());
        assertThat(allQuestions, notNullValue());
        assertTrue(allQuestions.containsAll(savedQuestions));
    }

    @Test
    public void findByIdTest() {
        Question generateQuestion = QuestionUtils.generateQuestion();
        Question savedQuestion = questionRepository.save(generateQuestion);
        Question question = questionRepository.findById(savedQuestion.getId());

        assertThat(savedQuestion, notNullValue());
        assertThat(question, notNullValue());
        assertThat(savedQuestion, equalTo(question));
    }

    @Test
    public void saveTest() {
        Question generatedQuestion = QuestionUtils.generateQuestion();
        int accountsCountBeforeInsert = questionRepository.findAll().size();
        Question user = questionRepository.save(generatedQuestion);
        List<Question> allQuestions = questionRepository.findAll();

        assertThat(user.getId(), notNullValue());
        assertThat(accountsCountBeforeInsert + 1, equalTo(allQuestions.size()));
        assertThat(allQuestions, hasItem(user));
    }

    @Test
    public void updateTest() {
        String dataForUpdate = "Some title";
        Question generatedQuestion = QuestionUtils.generateQuestion();
        Question savedQuestion = questionRepository.save(generatedQuestion);
        generatedQuestion.setTitle(dataForUpdate);
        Question updatedQuestion = questionRepository.update(savedQuestion.getId(), generatedQuestion);


        assertThat(savedQuestion, notNullValue());
        assertThat(updatedQuestion, notNullValue());
        assertThat(updatedQuestion.getId(), equalTo(updatedQuestion.getId()));
        assertThat(savedQuestion.getTitle(), not(equalTo(updatedQuestion.getTitle())));
    }

    @Test
    public void deleteTest() {
        Question generatedQuestion = QuestionUtils.generateQuestion();
        Question savedQuestion = questionRepository.save(generatedQuestion);
        List<Question> allQuestionsBeforeDeleting = questionRepository.findAll();
        questionRepository.delete(savedQuestion.getId());
        List<Question> allQuestionsAfterDeleting = questionRepository.findAll();

        assertThat(savedQuestion, notNullValue());
        assertThat(allQuestionsBeforeDeleting, notNullValue());
        assertThat(allQuestionsAfterDeleting, notNullValue());
        assertThat(allQuestionsBeforeDeleting, hasItem(savedQuestion));
        assertThat(allQuestionsAfterDeleting, not(hasItem(savedQuestion)));
    }
}