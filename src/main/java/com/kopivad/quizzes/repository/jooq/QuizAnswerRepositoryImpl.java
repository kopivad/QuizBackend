package com.kopivad.quizzes.repository.jooq;

import com.kopivad.quizzes.domain.QuizAnswer;
import com.kopivad.quizzes.dto.QuizAnswerDto;
import com.kopivad.quizzes.repository.QuizAnswerRepository;
import lombok.RequiredArgsConstructor;
import org.jooq.DSLContext;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.kopivad.quizzes.domain.db.tables.QuizAnswers.QUIZ_ANSWERS;


@Repository
@RequiredArgsConstructor
public class QuizAnswerRepositoryImpl implements QuizAnswerRepository {
    private final DSLContext dslContext;

    @Override
    public int save(QuizAnswerDto dto) {
        return dslContext
                .insertInto(QUIZ_ANSWERS)
                .set(QUIZ_ANSWERS.QUESTION_ID, dto.getQuestionId())
                .set(QUIZ_ANSWERS.SESSION_ID, dto.getSessionId())
                .set(QUIZ_ANSWERS.ANSWER_ID, dto.getAnswerId())
                .execute();
    }

    @Override
    public List<QuizAnswer> findAllBySessionId(long sessionId) {
        return dslContext
                .selectFrom(QUIZ_ANSWERS)
                .where(QUIZ_ANSWERS.SESSION_ID.eq(sessionId))
                .fetchInto(QuizAnswer.class);
    }
}
