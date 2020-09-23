package com.kopivad.quizzes.repository.jooq;

import com.kopivad.quizzes.domain.EvaluationStep;
import com.kopivad.quizzes.dto.EvaluationStepDto;
import com.kopivad.quizzes.repository.EvaluationStepRepository;
import com.kopivad.quizzes.utils.EvaluationStepUtils;
import com.kopivad.quizzes.utils.QuizUtils;
import com.kopivad.quizzes.utils.TestUtils;
import com.kopivad.quizzes.utils.UserUtils;
import org.jooq.DSLContext;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;

public class EvaluationStepRepositoryImplTest {
    private final DSLContext dslContext = TestUtils.createTestDefaultDSLContext();
    private final EvaluationStepRepository evaluationStepRepository = new EvaluationStepRepositoryImpl(dslContext);

    @BeforeAll
    static void init() {
        UserUtils.insertDefaultUser();
        QuizUtils.insertDefaultQuiz();
    }

    @BeforeEach
    void setUp() {
        EvaluationStepUtils.deleteAll();
    }

    @Test
    void saveTest() {
        EvaluationStepDto evaluationStep = EvaluationStepUtils.generateStepDto();
        long actual = evaluationStepRepository.save(evaluationStep);

        assertThat(actual, notNullValue());
    }

    @Test
    void findByQuizIdTest() {
        int expected = EvaluationStepUtils.insertStepsWithSameQuizId();
        List<EvaluationStep> actual = evaluationStepRepository.findByQuizId(QuizUtils.TEST_QUIZ_ID);

        assertThat(actual.size(), is(expected));
    }

    @Test
    void saveAllTest() {
        int expected = 10;
        List<EvaluationStepDto> dto = EvaluationStepUtils.generateStepDtos(expected);
        int actual = evaluationStepRepository.saveAll(dto);

        assertThat(actual, is(expected));
    }
}