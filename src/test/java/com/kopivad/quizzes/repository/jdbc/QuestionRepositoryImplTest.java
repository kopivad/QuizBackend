package com.kopivad.quizzes.repository.jdbc;

import com.kopivad.quizzes.domain.Question;
import com.kopivad.quizzes.repository.utils.QuestionUtils;
import com.kopivad.quizzes.repository.QuestionRepository;
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
public class QuestionRepositoryImplTest {
    private static QuestionRepository questionRepository;

    @BeforeClass
    public static void init() {
        DataSource pgDataSource = TestUtils.createTestDefaultPgDataSource();
//        TestUtils.createQuestionsTableIfNotExists(pgDataSource);
        questionRepository = new QuestionRepositoryImpl(pgDataSource);
    }

    @Test
    public void findAllTest() {
        List<Question> generatedQuestions = QuestionUtils.generateQuestions(5);
        List<Question> savedQuestions = generatedQuestions
                .stream()
                .map(question -> questionRepository.save(question))
                .collect(Collectors.toList());
        List<Question> allQuestions = questionRepository.findAll();
        assertTrue(allQuestions.containsAll(savedQuestions));
    }

    @Test
    public void findByIdTest() {
        Question generatedQuestion = QuestionUtils.generateQuestion();
        Question savedQuestion = questionRepository.save(generatedQuestion);
        Question quiz = questionRepository.findById(savedQuestion.getId());
        assertEquals(savedQuestion, quiz);
    }

    @Test
    public void saveTest() {
        Question generatedQuestion = QuestionUtils.generateQuestion();
        int accountsCountBeforeInsert = questionRepository.findAll().size();
        Question quiz = questionRepository.save(generatedQuestion);
        List<Question> allQuestions = questionRepository.findAll();
        assertNotNull(quiz.getId());
        assertEquals(accountsCountBeforeInsert + 1, allQuestions.size());
        assertTrue(allQuestions.contains(quiz));
    }

    @Test
    public void updateTest() {
        String dataForUpdate = "Test title";
        Question generatedQuestion = QuestionUtils.generateQuestion();
        Question savedQuestion = questionRepository.save(generatedQuestion);
        generatedQuestion.setTitle(dataForUpdate);
        Question updatedQuestion = questionRepository.update(savedQuestion.getId(), generatedQuestion);
        assertEquals(savedQuestion.getId(), updatedQuestion.getId());
        assertNotEquals(savedQuestion.getTitle(), updatedQuestion.getTitle());
    }

    @Test
    public void deleteTest() {
        Question generatedQuestion = QuestionUtils.generateQuestion();
        Question savedQuestion = questionRepository.save(generatedQuestion);
        List<Question> allQuestions = questionRepository.findAll();
        assertTrue(allQuestions.contains(savedQuestion));
        questionRepository.delete(savedQuestion.getId());
        assertFalse(questionRepository.findAll().contains(savedQuestion));
    }
}