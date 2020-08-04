package com.kopivad.quizzes.repository.jooq;

import com.kopivad.quizzes.domain.User;
import com.kopivad.quizzes.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.jooq.DSLContext;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.util.List;

import static com.kopivad.quizzes.domain.db.tables.Usr.USR;
import static com.kopivad.quizzes.repository.jooq.RecordMappers.getUserFromRecordMapper;
import static org.apache.commons.lang3.math.NumberUtils.INTEGER_ZERO;

@Repository
@RequiredArgsConstructor
public class UserRepositoryImpl implements UserRepository {
    private final DSLContext dslContext;

    @Override
    public List<User> findAll() {
        return dslContext
                .selectFrom(USR)
                .fetch()
                .map(getUserFromRecordMapper());
    }

    @Override
    public User findById(Long id) {
        return dslContext
                .selectFrom(USR)
                .where(USR.ID.eq(id))
                .fetchOne()
                .map(getUserFromRecordMapper());
    }

    @Override
    public long save(User user) {
        return dslContext
                .insertInto(USR)
                .set(USR.NAME, user.getName())
                .set(USR.EMAIL, user.getEmail())
                .set(USR.PASSWORD, user.getPassword())
                .set(USR.CREATION_DATE, Timestamp.valueOf(user.getCreationDate()))
                .set(USR.ROLE, user.getRole().name())
                .set(USR.GROUP_ID, user.getGroup().getId())
                .returning(USR.ID)
                .fetchOne()
                .getId();
    }

    @Override
    public boolean update(User user) {
        int affectedRows = dslContext
                .update(USR)
                .set(USR.NAME, user.getName())
                .set(USR.EMAIL, user.getEmail())
                .set(USR.PASSWORD, user.getPassword())
                .set(USR.ROLE, user.getRole().name())
                .set(USR.GROUP_ID, user.getGroup().getId())
                .where(USR.ID.eq(user.getId()))
                .execute();

        return affectedRows > INTEGER_ZERO;
    }

    @Override
    public boolean delete(Long id) {
        int affectedRows = dslContext
                .delete(USR)
                .where(USR.ID.eq(id))
                .execute();
        return affectedRows > INTEGER_ZERO;
    }

    @Override
    public List<User> findByGroupId(long id) {
        return dslContext
                .selectFrom(USR)
                .where(USR.GROUP_ID.eq(id))
                .fetch()
                .map(getUserFromRecordMapper());
    }

    @Override
    public List<User> findByEmailStartsWith(String email) {
        return dslContext
                .selectFrom(USR)
                .where(USR.EMAIL.startsWithIgnoreCase(email))
                .fetch()
                .map(getUserFromRecordMapper());
    }
}
