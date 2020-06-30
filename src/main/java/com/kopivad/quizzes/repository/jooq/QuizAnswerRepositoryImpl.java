package com.kopivad.quizzes.repository.jooq;

import com.kopivad.quizzes.domain.*;
import com.kopivad.quizzes.repository.QuizAnswerRepository;
import lombok.RequiredArgsConstructor;
import org.jooq.DSLContext;
import org.jooq.Record;
import org.jooq.RecordMapper;
import org.springframework.stereotype.Repository;

import static com.kopivad.quizzes.domain.db.tables.QuizResults.QUIZ_RESULTS;


@Repository
@RequiredArgsConstructor
public class QuizAnswerRepositoryImpl implements QuizAnswerRepository {
    private final DSLContext dslContext;

    @Override
    public QuizAnswer save(QuizAnswer quizAnswer) {
        return dslContext
                .insertInto(QUIZ_RESULTS)
                .set(QUIZ_RESULTS.QUESTION_ID, quizAnswer.getQuestion().getId())
                .set(QUIZ_RESULTS.SESSION_ID, quizAnswer.getSession().getId())
                .set(QUIZ_RESULTS.ANSWER_ID, quizAnswer.getAnswer().getId())
                .returning(QUIZ_RESULTS.fields())
                .fetchOne()
                .map(getQuizResultRecordMapper());
    }

    private RecordMapper<Record, QuizAnswer> getQuizResultRecordMapper() {
        return r -> QuizAnswer
                .builder()
                .id(r.getValue(QUIZ_RESULTS.ID))
                .question(Question.builder().id(r.getValue(QUIZ_RESULTS.QUESTION_ID)).build())
                .answer(Answer.builder().id(r.getValue(QUIZ_RESULTS.ANSWER_ID)).build())
                .session(QuizSession.builder().id(r.getValue(QUIZ_RESULTS.SESSION_ID)).build())
                .build();
    }
}
