package com.kopivad.quizzes.repository.jooq;

import com.kopivad.quizzes.domain.Quiz;
import com.kopivad.quizzes.domain.db.tables.records.QuizzesGroupsRecord;
import com.kopivad.quizzes.repository.QuizRepository;
import lombok.RequiredArgsConstructor;
import org.jooq.DSLContext;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.util.List;
import java.util.stream.Collectors;

import static com.kopivad.quizzes.domain.db.tables.Quizzes.QUIZZES;
import static com.kopivad.quizzes.domain.db.tables.QuizzesGroups.QUIZZES_GROUPS;
import static com.kopivad.quizzes.repository.jooq.RecordMappers.getQuizFromRecordMapper;
import static org.apache.commons.lang3.math.NumberUtils.INTEGER_ZERO;

@Repository
@RequiredArgsConstructor
public class QuizRepositoryImpl implements QuizRepository {
    private final DSLContext dslContext;

    @Override
    public List<Quiz> findAll() {
        return dslContext
                .selectFrom(QUIZZES)
                .fetch()
                .map(getQuizFromRecordMapper());
    }

    @Override
    public Quiz findById(Long id) {
        return dslContext
                .selectFrom(QUIZZES)
                .where(QUIZZES.ID.eq(id))
                .fetchOne()
                .map(getQuizFromRecordMapper());
    }

    @Override
    public long save(Quiz quiz) {
        return dslContext
                .insertInto(QUIZZES)
                .set(QUIZZES.TITLE, quiz.getTitle())
                .set(QUIZZES.ACTIVE, quiz.isActive())
                .set(QUIZZES.TOTAL, quiz.getTotal())
                .set(QUIZZES.DESCRIPTION, quiz.getDescription())
                .set(QUIZZES.AUTHOR_ID, quiz.getAuthor().getId())
                .set(QUIZZES.CREATION_DATE, Timestamp.valueOf(quiz.getCreationDate()))
                .returning(QUIZZES.ID)
                .fetchOne()
                .getId();
    }

    @Override
    public boolean update(Quiz quiz) {
        int affectedRows = dslContext
                .update(QUIZZES)
                .set(QUIZZES.TITLE, quiz.getTitle())
                .set(QUIZZES.DESCRIPTION, quiz.getDescription())
                .set(QUIZZES.ACTIVE, quiz.isActive())
                .set(QUIZZES.TOTAL, quiz.getTotal())
                .set(QUIZZES.AUTHOR_ID, quiz.getAuthor().getId())
                .where(QUIZZES.ID.eq(quiz.getId()))
                .execute();

        return affectedRows > INTEGER_ZERO;
    }

    @Override
    public boolean delete(Long id) {
        int affectedRows = dslContext
                .deleteFrom(QUIZZES)
                .where(QUIZZES.ID.eq(id))
                .execute();

        return affectedRows > INTEGER_ZERO;
    }

    @Override
    public List<Quiz> findByGroupId(long id) {
        List<Long> ids = dslContext
                .selectFrom(QUIZZES_GROUPS)
                .where(QUIZZES_GROUPS.GROUP_ID.eq(id))
                .fetch()
                .stream()
                .map(QuizzesGroupsRecord::getQuizId)
                .collect(Collectors.toUnmodifiableList());

        return dslContext
                .selectFrom(QUIZZES)
                .where(QUIZZES.ID.in(ids))
                .fetch()
                .map(getQuizFromRecordMapper());
    }

    @Override
    public List<Quiz> findByTitleStartsWith(String title) {
        return dslContext
                .selectFrom(QUIZZES)
                .where(QUIZZES.TITLE.startsWithIgnoreCase(title))
                .fetch()
                .map(getQuizFromRecordMapper());
    }
}
