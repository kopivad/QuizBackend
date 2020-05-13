package com.kopivad.quizzes.repository.jooq;

import com.kopivad.quizzes.domain.Question;
import com.kopivad.quizzes.domain.QuestionType;
import com.kopivad.quizzes.domain.Quiz;
import com.kopivad.quizzes.repository.QuestionRepository;
import lombok.RequiredArgsConstructor;
import org.jooq.DSLContext;
import org.jooq.Record;
import org.jooq.RecordMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.kopivad.quizzes.domain.db.tables.Questions.QUESTIONS;

@Repository("jooqQuestionRepository")
@RequiredArgsConstructor
public class QuestionRepositoryImpl implements QuestionRepository {
    private final DSLContext dslContext;

    @Override
    public List<Question> findAll() {
        return dslContext
                .selectFrom(QUESTIONS)
                .fetch()
                .map(getQuestionFromRecordMapper());
    }

    @Override
    public Question findById(Long id) {
        return dslContext
                .selectFrom(QUESTIONS)
                .where(QUESTIONS.ID.eq(id))
                .fetchOne()
                .map(getQuestionFromRecordMapper());
    }

    @Override
    public Question save(Question question) {
        return dslContext
                .insertInto(QUESTIONS)
                .set(QUESTIONS.TITLE, question.getTitle())
                .set(QUESTIONS.TYPE, question.getType().name())
                .set(QUESTIONS.QUIZ_ID, question.getQuiz().getId())
                .returning(QUESTIONS.fields())
                .fetchOne()
                .map(getQuestionFromRecordMapper());
    }

    @Override
    public Question update(Long id, Question question) {
        return dslContext
                .update(QUESTIONS)
                .set(QUESTIONS.TITLE, question.getTitle())
                .set(QUESTIONS.TYPE, question.getType().name())
                .set(QUESTIONS.QUIZ_ID, question.getQuiz().getId())
                .where(QUESTIONS.ID.eq(id))
                .returningResult(QUESTIONS.fields())
                .fetchOne()
                .map(getQuestionFromRecordMapper());
    }

    @Override
    public void delete(Long id) {
        dslContext
                .deleteFrom(QUESTIONS)
                .where(QUESTIONS.ID.eq(id))
                .execute();
    }

    @Override
    public List<Question> findByQuizId(Long id) {
        return dslContext
                .selectFrom(QUESTIONS)
                .where(QUESTIONS.QUIZ_ID.eq(id))
                .fetch()
                .map(getQuestionFromRecordMapper());
    }

    private RecordMapper<Record, Question> getQuestionFromRecordMapper() {
        return record -> Question
                .builder()
                .id(record.getValue(QUESTIONS.ID))
                .type(QuestionType.valueOf(record.getValue(QUESTIONS.TYPE)))
                .title(record.getValue(QUESTIONS.TITLE))
                .quiz(Quiz.builder().id(record.getValue(QUESTIONS.QUIZ_ID)).build())
                .build();
    }
}
