package com.kopivad.quizzes.repository.jooq;

import com.kopivad.quizzes.domain.Quiz;
import com.kopivad.quizzes.dto.QuizDto;
import com.kopivad.quizzes.repository.QuizRepository;
import lombok.RequiredArgsConstructor;
import org.jooq.DSLContext;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static com.kopivad.quizzes.domain.db.tables.GroupsQuizzes.GROUPS_QUIZZES;
import static com.kopivad.quizzes.domain.db.tables.Quizzes.QUIZZES;

@Repository
@RequiredArgsConstructor
public class QuizRepositoryImpl implements QuizRepository {
    private final DSLContext dslContext;

    @Override
    public List<Quiz> findAll() {
        return dslContext
                .selectFrom(QUIZZES)
                .fetchInto(Quiz.class);
    }

    @Override
    public Optional<Quiz> findById(Long id) {
        return dslContext
                .selectFrom(QUIZZES)
                .where(QUIZZES.ID.eq(id))
                .fetchOptionalInto(Quiz.class);
    }

    @Override
    public long save(QuizDto quiz) {
        return dslContext
                .insertInto(QUIZZES)
                .set(QUIZZES.TITLE, quiz.getTitle())
                .set(QUIZZES.ACTIVE, true)
                .set(QUIZZES.TOTAL, quiz.getTotal())
                .set(QUIZZES.DESCRIPTION, quiz.getDescription())
                .set(QUIZZES.AUTHOR_ID, quiz.getAuthorId())
                .set(QUIZZES.CREATION_DATE, Timestamp.valueOf(LocalDateTime.now()))
                .returning(QUIZZES.ID)
                .fetchOne()
                .getId();
    }

    @Override
    public int update(Quiz quiz) {
        return dslContext
                .update(QUIZZES)
                .set(QUIZZES.TITLE, quiz.getTitle())
                .set(QUIZZES.DESCRIPTION, quiz.getDescription())
                .set(QUIZZES.ACTIVE, quiz.isActive())
                .set(QUIZZES.TOTAL, quiz.getTotal())
                .where(QUIZZES.ID.eq(quiz.getId()))
                .execute();
    }

    @Override
    public int delete(Long id) {
        return dslContext
                .deleteFrom(QUIZZES)
                .where(QUIZZES.ID.eq(id))
                .execute();
    }

    @Override
    public List<Quiz> findByGroupId(long id) {
        List<Long> ids = getQuizGroupsIds(id);

        return dslContext
                .selectFrom(QUIZZES)
                .where(QUIZZES.ID.in(ids))
                .fetchInto(Quiz.class);
    }

    private List<Long> getQuizGroupsIds(long id) {
        return dslContext
                .select(GROUPS_QUIZZES.QUIZ_ID)
                .from(GROUPS_QUIZZES)
                .where(GROUPS_QUIZZES.GROUP_ID.eq(id))
                .fetchInto(Long.class);
    }

    @Override
    public List<Quiz> findByTitleStartsWith(String title) {
        return dslContext
                .selectFrom(QUIZZES)
                .where(QUIZZES.TITLE.startsWithIgnoreCase(title))
                .fetchInto(Quiz.class);
    }
}
