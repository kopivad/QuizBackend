package com.kopivad.quizzes.repository.jooq;

import com.kopivad.quizzes.domain.Answer;
import com.kopivad.quizzes.domain.Question;
import com.kopivad.quizzes.domain.QuizAnswer;
import com.kopivad.quizzes.domain.QuizSession;
import com.kopivad.quizzes.domain.db.tables.records.QuizAnswersRecord;
import com.kopivad.quizzes.repository.QuizAnswerRepository;
import lombok.RequiredArgsConstructor;
import org.jooq.DSLContext;
import org.jooq.RecordMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.kopivad.quizzes.domain.db.tables.QuizAnswers.QUIZ_ANSWERS;


@Repository
@RequiredArgsConstructor
public class QuizAnswerRepositoryImpl implements QuizAnswerRepository {
    private final DSLContext dslContext;

    @Override
    public long save(QuizAnswer quizAnswer) {
        return dslContext
                .insertInto(QUIZ_ANSWERS)
                .set(QUIZ_ANSWERS.QUESTION_ID, quizAnswer.getQuestion().getId())
                .set(QUIZ_ANSWERS.SESSION_ID, quizAnswer.getSession().getId())
                .set(QUIZ_ANSWERS.ANSWER_ID, quizAnswer.getAnswer().getId())
                .returning(QUIZ_ANSWERS.ID)
                .fetchOne()
                .getId();
    }

    @Override
    public List<QuizAnswer> findAllBySessionId(long sessionId) {
        return dslContext
                .selectFrom(QUIZ_ANSWERS)
                .where(QUIZ_ANSWERS.SESSION_ID.eq(sessionId))
                .fetch()
                .map(getQuizAnswersRecordQuizAnswerRecordMapper()
                );
    }

    public RecordMapper<QuizAnswersRecord, QuizAnswer> getQuizAnswersRecordQuizAnswerRecordMapper() {
        return r -> QuizAnswer
                .builder()
                .id(r.getValue(QUIZ_ANSWERS.ID))
                .answer(Answer.builder().id(r.getValue(QUIZ_ANSWERS.ANSWER_ID)).build())
                .question(Question.builder().id(r.getValue(QUIZ_ANSWERS.QUESTION_ID)).build())
                .session(QuizSession.builder().id(r.getValue(QUIZ_ANSWERS.SESSION_ID)).build())
                .build();
    }
}
