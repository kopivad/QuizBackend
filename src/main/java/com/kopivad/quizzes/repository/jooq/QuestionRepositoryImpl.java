package com.kopivad.quizzes.repository.jooq;

import com.kopivad.quizzes.domain.Question;
import com.kopivad.quizzes.domain.db.tables.records.QuestionsRecord;
import com.kopivad.quizzes.dto.QuestionDto;
import com.kopivad.quizzes.repository.QuestionRepository;
import lombok.RequiredArgsConstructor;
import org.jooq.DSLContext;
import org.jooq.InsertValuesStep4;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.kopivad.quizzes.domain.db.tables.Questions.QUESTIONS;

@Repository
@RequiredArgsConstructor
public class QuestionRepositoryImpl implements QuestionRepository {
    private final DSLContext dslContext;

    @Override
    public List<Question> findAll() {
        return dslContext
                .selectFrom(QUESTIONS)
                .fetchInto(Question.class);
    }

    @Override
    public Optional<Question> findById(Long id) {
        return dslContext
                .selectFrom(QUESTIONS)
                .where(QUESTIONS.ID.eq(id))
                .fetchOptionalInto(Question.class);
    }

    @Override
    public long save(QuestionDto question) {
        return dslContext
                .insertInto(QUESTIONS)
                .set(QUESTIONS.TITLE, question.getTitle())
                .set(QUESTIONS.TYPE, question.getType().name())
                .set(QUESTIONS.VALUE, question.getValue())
                .set(QUESTIONS.QUIZ_ID, question.getQuizId())
                .returning(QUESTIONS.ID)
                .fetchOne()
                .getId();
    }

    @Override
    public int update(Question question) {
        return dslContext
                .update(QUESTIONS)
                .set(QUESTIONS.TITLE, question.getTitle())
                .set(QUESTIONS.TYPE, question.getType().name())
                .set(QUESTIONS.VALUE, question.getValue())
                .where(QUESTIONS.ID.eq(question.getId()))
                .execute();
    }

    @Override
    public int delete(Long id) {
        return dslContext
                .deleteFrom(QUESTIONS)
                .where(QUESTIONS.ID.eq(id))
                .execute();
    }

    @Override
    public List<Question> findByQuizId(Long id) {
        return dslContext
                .selectFrom(QUESTIONS)
                .where(QUESTIONS.QUIZ_ID.eq(id))
                .fetchInto(Question.class);
    }

    @Override
    public int saveAll(List<QuestionDto> dtos) {
        return dslContext.batch(getInsertValues(dtos)).execute().length;
    }

    private List<InsertValuesStep4<QuestionsRecord, String, String, Integer, Long>> getInsertValues(List<QuestionDto> dtos) {
        return dtos.stream()
        .map(dto -> dslContext
                .insertInto(QUESTIONS, QUESTIONS.TITLE, QUESTIONS.TYPE, QUESTIONS.VALUE, QUESTIONS.QUIZ_ID)
                .values(dto.getTitle(), dto.getType().name(), dto.getValue(), dto.getQuizId()))
        .collect(Collectors.toUnmodifiableList());
    }
}
