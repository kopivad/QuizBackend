package com.kopivad.quizzes.repository.jooq;

import com.kopivad.quizzes.domain.QuizAnswer;
import com.kopivad.quizzes.repository.QuizAnswerRepository;
import lombok.RequiredArgsConstructor;
import org.jooq.DSLContext;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.kopivad.quizzes.domain.db.tables.QuizAnswers.QUIZ_ANSWERS;
import static org.apache.commons.lang3.math.NumberUtils.INTEGER_ZERO;


@Repository
@RequiredArgsConstructor
public class QuizAnswerRepositoryImpl implements QuizAnswerRepository {
    private final DSLContext dslContext;

    @Override
    public boolean save(QuizAnswer quizAnswer) {
        int affectedRows = dslContext
                .insertInto(QUIZ_ANSWERS)
                .set(QUIZ_ANSWERS.QUESTION_ID, quizAnswer.getQuestionId())
                .set(QUIZ_ANSWERS.SESSION_ID, quizAnswer.getSessionId())
                .set(QUIZ_ANSWERS.ANSWER_ID, quizAnswer.getAnswerId())
                .execute();

        return affectedRows > INTEGER_ZERO;
    }

    @Override
    public List<QuizAnswer> findAllBySessionId(long sessionId) {
        return dslContext
                .selectFrom(QUIZ_ANSWERS)
                .where(QUIZ_ANSWERS.SESSION_ID.eq(sessionId))
                .fetchInto(QuizAnswer.class);
    }
}
