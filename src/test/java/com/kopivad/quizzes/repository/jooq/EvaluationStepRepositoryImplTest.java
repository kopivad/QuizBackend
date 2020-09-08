package com.kopivad.quizzes.repository.jooq;

import com.kopivad.quizzes.domain.EvaluationStep;
import com.kopivad.quizzes.domain.Quiz;
import com.kopivad.quizzes.repository.EvaluationStepRepository;
import com.kopivad.quizzes.repository.QuizRepository;
import com.kopivad.quizzes.utils.EvaluationStepUtils;
import com.kopivad.quizzes.utils.QuizUtils;
import com.kopivad.quizzes.utils.TestUtils;
import org.jooq.DSLContext;
import org.jooq.SQLDialect;
import org.jooq.impl.DSL;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.stream.Collectors;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class EvaluationStepRepositoryImplTest {
    private static EvaluationStepRepository evaluationStepRepository;
    private static QuizRepository quizRepository;

    @BeforeAll
    public static void init() {
        DSLContext dslContext = DSL.using(TestUtils.createTestDefaultPgDataSource(), SQLDialect.POSTGRES);
        evaluationStepRepository = new EvaluationStepRepositoryImpl(dslContext);
        quizRepository = new QuizRepositoryImpl(dslContext);
    }

    @Test
    public void saveTest() {
        EvaluationStep evaluationStep = EvaluationStepUtils.generateStep();
        long actual = evaluationStepRepository.save(evaluationStep);

        assertThat(actual, notNullValue());
    }

    @Test
    public void findByQuizIdTest() {
        Quiz quiz = QuizUtils.generateQuiz();
        long quizId = quizRepository.save(quiz);
        int size = 5;
        List<EvaluationStep> expected = EvaluationStepUtils.generateSteps(quizId, size)
                .stream()
                .map(s -> s.toBuilder().id(evaluationStepRepository.save(s)).build())
                .collect(Collectors.toUnmodifiableList());
        List<EvaluationStep> actual = evaluationStepRepository.findByQuizId(quizId);

        assertTrue(actual.containsAll(expected));
    }
}