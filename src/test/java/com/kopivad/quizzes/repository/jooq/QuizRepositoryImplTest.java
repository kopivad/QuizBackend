package com.kopivad.quizzes.repository.jooq;

import com.kopivad.quizzes.domain.Quiz;
import com.kopivad.quizzes.repository.QuizRepository;
import com.kopivad.quizzes.utils.QuizUtils;
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

public class QuizRepositoryImplTest {
    private static DSLContext dslContext;
    private static QuizRepository quizRepository;


    @BeforeClass
    public static void init() {
        dslContext = DSL.using(TestUtils.createTestDefaultPgDataSource(), SQLDialect.POSTGRES);
        quizRepository = new QuizRepositoryImpl(dslContext);
    }

    @Test
    public void findAllTest() {
        List<Quiz> generatedQuizzes = QuizUtils.generateQuizzes(5);
        List<Quiz> savedQuizzes = generatedQuizzes
                .stream()
                .map(quiz -> quizRepository.save(quiz))
                .collect(Collectors.toUnmodifiableList());
        List<Quiz> quizzes = quizRepository.findAll();

        assertThat(savedQuizzes, notNullValue());
        assertThat(quizzes, notNullValue());
        assertTrue(quizzes.containsAll(savedQuizzes));
    }

    @Test
    public void findByIdTest() {
        Quiz generatedQuiz = QuizUtils.generateQuiz();
        Quiz savedQuiz = quizRepository.save(generatedQuiz);
        Quiz quiz = quizRepository.findById(savedQuiz.getId());

        assertThat(savedQuiz, notNullValue());
        assertThat(quiz, notNullValue());
        assertThat(savedQuiz, equalTo(quiz));
    }

    @Test
    public void saveTest() {
        Quiz generatedQuiz = QuizUtils.generateQuiz();
        int quizzesCountBeforeInsert = quizRepository.findAll().size();
        Quiz quiz = quizRepository.save(generatedQuiz);
        List<Quiz> quizzes = quizRepository.findAll();

        assertThat(quiz.getId(), notNullValue());
        assertThat(quizzesCountBeforeInsert + 1, equalTo(quizzes.size()));
        assertThat(quizzes, hasItem(quiz));
    }

    @Test
    public void updateTest() {
        String dataForUpdate = "some text";
        Quiz generatedQuiz = QuizUtils.generateQuiz();
        Quiz savedQuiz = quizRepository.save(generatedQuiz);
        Quiz quizForUpdate = generatedQuiz.toBuilder().title(dataForUpdate).build();
        Quiz updatedQuiz = quizRepository.update(savedQuiz.getId(), quizForUpdate);


        assertThat(savedQuiz, notNullValue());
        assertThat(updatedQuiz, notNullValue());
        assertThat(savedQuiz.getId(), equalTo(updatedQuiz.getId()));
        assertNotEquals(savedQuiz.getTitle(), not(equalTo(updatedQuiz.getTitle())));
    }

    @Test
    public void deleteTest() {
        Quiz generateQuiz = QuizUtils.generateQuiz();
        Quiz savedQuiz = quizRepository.save(generateQuiz);
        List<Quiz> allQuizzesBeforeDeleting = quizRepository.findAll();
        quizRepository.delete(savedQuiz.getId());
        List<Quiz> allQuizzesAfterDeleting = quizRepository.findAll();

        assertThat(savedQuiz, notNullValue());
        assertThat(allQuizzesBeforeDeleting, notNullValue());
        assertThat(allQuizzesAfterDeleting, notNullValue());
        assertThat(allQuizzesBeforeDeleting, hasItem(savedQuiz));
        assertThat(allQuizzesAfterDeleting, not(hasItem(savedQuiz)));
    }
}