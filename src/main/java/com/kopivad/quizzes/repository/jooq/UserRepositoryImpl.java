package com.kopivad.quizzes.repository.jooq;

import com.kopivad.quizzes.domain.User;
import com.kopivad.quizzes.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.jooq.DSLContext;
import org.jooq.Record;
import org.jooq.RecordMapper;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.util.List;

import static com.kopivad.quizzes.domain.db.tables.Usr.USR;

@Repository("jooqUserRepository")
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
    public User save(User user) {
        return dslContext
                .insertInto(USR, USR.NAME, USR.EMAIL, USR.PASSWORD, USR.ROLE, USR.CREATION_DATE)
                .values(user.getName(), user.getEmail(), user.getPassword(), user.getRole(), Timestamp.valueOf(user.getCreationDate()))
                .returning(USR.ID, USR.NAME, USR.EMAIL, USR.PASSWORD, USR.ROLE, USR.CREATION_DATE)
                .fetchOne()
                .map(getUserFromRecordMapper());
    }

    @Override
    public User update(Long id, User user) {
        return dslContext
                .update(USR)
                .set(USR.NAME, user.getName())
                .set(USR.EMAIL, user.getEmail())
                .set(USR.ROLE, user.getRole())
                .set(USR.PASSWORD, user.getPassword())
                .where(USR.ID.eq(id))
                .returningResult(USR.ID, USR.NAME, USR.EMAIL, USR.PASSWORD, USR.ROLE, USR.CREATION_DATE)
                .fetchOne()
                .map(getUserFromRecordMapper());
    }

    @Override
    public void delete(Long id) {
        dslContext
                .delete(USR)
                .where(USR.ID.eq(id))
                .execute();
    }

    @Override
    public User findByEmail(String email) {
        return dslContext
                .selectFrom(USR)
                .where(USR.EMAIL.eq(email))
                .fetchOne()
                .map(getUserFromRecordMapper());
    }

    private RecordMapper<Record, User> getUserFromRecordMapper() {
        return record -> User
                .builder()
                .id(record.getValue(USR.ID))
                .name(record.getValue(USR.NAME))
                .email(record.getValue(USR.EMAIL))
                .role(record.getValue(USR.ROLE))
                .creationDate(record.getValue(USR.CREATION_DATE).toLocalDateTime())
                .password(record.getValue(USR.PASSWORD))
                .build();
    }
}
