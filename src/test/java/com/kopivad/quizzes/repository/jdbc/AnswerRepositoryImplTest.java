package com.kopivad.quizzes.repository.jdbc;

import com.kopivad.quizzes.domain.Answer;
import com.kopivad.quizzes.domain.utils.AnswerUtils;
import com.kopivad.quizzes.repository.AnswerRepository;
import com.kopivad.quizzes.repository.utils.TestUtils;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import javax.sql.DataSource;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.Assert.*;

@RunWith(JUnit4.class)
public class AnswerRepositoryImplTest {
    private static AnswerRepository answerRepository;

    @BeforeClass
    public static void init() {
        DataSource pgDataSource = TestUtils.createTestDefaultPgDataSource();
//        TestUtils.createAnswersTableIfNotExists(pgDataSource);
        answerRepository = new AnswerRepositoryImpl(pgDataSource);
    }

    @Test
    public void findAllTest() {
        List<Answer> generatedQuizzes = AnswerUtils.generateAnswers(5);
        List<Answer> savedQuizzes = generatedQuizzes
                .stream()
                .map(answer -> answerRepository.save(answer))
                .collect(Collectors.toList());
        List<Answer> allAnswers = answerRepository.findAll();
        assertTrue(allAnswers.containsAll(savedQuizzes));
    }

    @Test
    public void findByIdTest() {
        Answer generatedAnswer = AnswerUtils.generateAnswer();
        Answer savedAnswer = answerRepository.save(generatedAnswer);
        Answer answer = answerRepository.findById(savedAnswer.getId());
        assertEquals(savedAnswer, answer);
    }

    @Test
    public void saveTest() {
        Answer generatedAnswer = AnswerUtils.generateAnswer();
        int accountsCountBeforeInsert = answerRepository.findAll().size();
        Answer answer = answerRepository.save(generatedAnswer);
        List<Answer> allAnswers = answerRepository.findAll();
        assertNotNull(answer.getId());
        assertEquals(accountsCountBeforeInsert + 1, allAnswers.size());
        assertTrue(allAnswers.contains(answer));
    }

    @Test
    public void updateTest() {
        String dataForUpdate = "Test text";
        Answer generatedAnswer = AnswerUtils.generateAnswer();
        Answer savedAnswer = answerRepository.save(generatedAnswer);
        generatedAnswer.setText(dataForUpdate);
        Answer updatedQuiz = answerRepository.update(savedAnswer.getId(), generatedAnswer);
        assertEquals(savedAnswer.getId(), updatedQuiz.getId());
        assertNotEquals(savedAnswer.getText(), updatedQuiz.getText());
    }

    @Test
    public void deleteTest() {
        Answer generatedAnswer = AnswerUtils.generateAnswer();
        Answer savedAnswer = answerRepository.save(generatedAnswer);
        List<Answer> AllAnswers = answerRepository.findAll();
        assertTrue(AllAnswers.contains(savedAnswer));
        answerRepository.delete(savedAnswer.getId());
        assertFalse(answerRepository.findAll().contains(savedAnswer));
    }
}