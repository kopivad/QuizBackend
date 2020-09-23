package com.kopivad.quizzes.repository.jooq;

import com.kopivad.quizzes.domain.Question;
import com.kopivad.quizzes.dto.QuestionDto;
import com.kopivad.quizzes.repository.QuestionRepository;
import com.kopivad.quizzes.utils.QuestionUtils;
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

public class QuestionRepositoryImplTest {
    private final DSLContext dslContext = TestUtils.createTestDefaultDSLContext();
    private final QuestionRepository questionRepository = new QuestionRepositoryImpl(dslContext);

    @BeforeAll
    static void init() {
        UserUtils.insertDefaultUser();
        QuizUtils.insertDefaultQuiz();
    }

    @BeforeEach
    void setUp() {
        QuestionUtils.deleteAll();
    }

    @Test
    public void findAllTest() {
        int expected = 10;
        QuestionUtils.insertRandomQuestionsInDb(expected);
        List<Question> actual = questionRepository.findAll();

        assertThat(actual.size(), is(expected));
    }

    @Test
    public void findByIdTest() {
        long id = QuestionUtils.insertRandomQuestionInDb();
        Optional<Question> actual = questionRepository.findById(id);

        assertThat(actual.isPresent(), is(true));
    }

    @Test
    public void saveTest() {
        QuestionDto question = QuestionUtils.generateQuestionDto();
        long actual = questionRepository.save(question);

        assertThat(actual, notNullValue());
    }

    @Test
    public void updateTest() {
        long id = QuestionUtils.insertRandomQuestionInDb();
        Question question = QuestionUtils.generateQuestion(id);
        int expected = 1;
        int actual = questionRepository.update(question);

        assertThat(actual, is(expected));
    }

    @Test
    public void deleteTest() {
        long id = QuestionUtils.insertRandomQuestionInDb();
        int expected = 1;
        int actual = questionRepository.delete(id);

        assertThat(actual, is(expected));
    }
}