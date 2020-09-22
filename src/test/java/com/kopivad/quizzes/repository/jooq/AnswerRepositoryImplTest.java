package com.kopivad.quizzes.repository.jooq;

import com.kopivad.quizzes.domain.Answer;
import com.kopivad.quizzes.dto.AnswerDto;
import com.kopivad.quizzes.repository.AnswerRepository;
import com.kopivad.quizzes.utils.*;
import org.jooq.DSLContext;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertTrue;


public class AnswerRepositoryImplTest {
    private final DSLContext dslContext = TestUtils.createTestDefaultDSLContext();
    private final AnswerRepository answerRepository = new AnswerRepositoryImpl(dslContext);

    @BeforeAll
   static void init() {
        UserUtils.insertDefaultUser();
        QuizUtils.insertDefaultQuiz();
        QuestionUtils.insertDefaultQuestion();
    }

    @BeforeEach
    void setUp() {
        AnswerUtils.deleteAll();
    }

    @Test
    void findAllTest() {
        int expected = 10;
        AnswerUtils.insertRandomAnswersInDb(expected);
        List<Answer> actual = answerRepository.findAll();

        assertThat(expected, is(actual.size()));
    }

    @Test
    void findByIdTest() {
        long id = AnswerUtils.insertRandomAnswerInDb();
        Optional<Answer> actual = answerRepository.findById(id);

        assertTrue(actual.isPresent());
    }

    @Test
    void saveTest() {
        AnswerDto dto = AnswerUtils.generateAnswerDto();
        int expected = 1;
        int actual = answerRepository.save(dto);

        assertThat(actual, is(expected));
    }

    @Test
    void updateTest() {
        long id = AnswerUtils.insertRandomAnswerInDb();
        Answer answer = AnswerUtils.generateAnswer(id);
        int expected = 1;
        int actual = answerRepository.update(answer);

        assertThat(actual, is(expected));
    }

    @Test
    void deleteTest() {
        long id = AnswerUtils.insertRandomAnswerInDb();
        int expected = 1;
        int actual = answerRepository.delete(id);

        assertThat(actual, is(expected));
    }

    @Test
    void findByQuestionIdTest() {
        int expected = 10;
        AnswerUtils.insertRandomAnswersInDb(expected);
        List<Answer> actual = answerRepository.findByQuestionId(QuestionUtils.TEST_QUESTION_ID);

        assertThat(actual.size(), is(expected));
    }

    @Test
    void saveAllTest() {
        int expected = 10;
        List<AnswerDto> dtos = AnswerUtils.generateAnswerDtos(expected);
        int actual = answerRepository.saveAll(dtos);

        assertThat(actual, is(expected));
    }
}