package com.kopivad.quizzes.repository.jdbc;

import com.kopivad.quizzes.domain.Quiz;
import com.kopivad.quizzes.domain.utils.QuizUtils;
import com.kopivad.quizzes.repository.QuizRepository;
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
public class QuizRepositoryImplTest {
    private static QuizRepository quizRepository;

    @BeforeClass
    public static void init() {
        DataSource pgDataSource = TestUtils.createTestDefaultPgDataSource();
//        TestUtils.createQuizzesTableIfNotExists(pgDataSource);
        quizRepository = new QuizRepositoryImpl(pgDataSource);
    }

    @Test
    public void findAllTest() {
        List<Quiz> generatedQuizzes = QuizUtils.generateQuizzes(5);
        List<Quiz> savedQuizzes = generatedQuizzes
                .stream()
                .map(quiz -> quizRepository.save(quiz))
                .collect(Collectors.toList());
        List<Quiz> allQuizzes = quizRepository.findAll();
        assertTrue(allQuizzes.containsAll(savedQuizzes));
    }

    @Test
    public void findByIdTest() {
        Quiz generatedQuiz = QuizUtils.generateQuiz();
        Quiz savedQuiz = quizRepository.save(generatedQuiz);
        Quiz quiz = quizRepository.findById(savedQuiz.getId());
        assertEquals(savedQuiz, quiz);
    }

    @Test
    public void saveTest() {
        Quiz generatedQuiz = QuizUtils.generateQuiz();
        int accountsCountBeforeInsert = quizRepository.findAll().size();
        Quiz quiz = quizRepository.save(generatedQuiz);
        List<Quiz> allQuizzes = quizRepository.findAll();
        assertNotNull(quiz.getId());
        assertEquals(accountsCountBeforeInsert + 1, allQuizzes.size());
        assertTrue(allQuizzes.contains(quiz));
    }

    @Test
    public void updateTest() {
        String dataForUpdate = "Test description";
        Quiz generatedQuiz = QuizUtils.generateQuiz();
        Quiz savedQuiz = quizRepository.save(generatedQuiz);
        generatedQuiz.setDescription(dataForUpdate);
        Quiz updatedQuiz = quizRepository.update(savedQuiz.getId(), generatedQuiz);
        assertEquals(savedQuiz.getId(), updatedQuiz.getId());
        assertNotEquals(savedQuiz.getDescription(), updatedQuiz.getDescription());
    }

    @Test
    public void deleteTest() {
        Quiz generatedQuiz = QuizUtils.generateQuiz();
        Quiz savedQuiz = quizRepository.save(generatedQuiz);
        List<Quiz> allQuizzes = quizRepository.findAll();
        assertTrue(allQuizzes.contains(savedQuiz));
        quizRepository.delete(savedQuiz.getId());
        assertFalse(quizRepository.findAll().contains(savedQuiz));
    }
}