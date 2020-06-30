package com.kopivad.quizzes.repository.jooq;

import com.kopivad.quizzes.domain.Role;
import com.kopivad.quizzes.domain.User;
import com.kopivad.quizzes.repository.RoleRepository;
import com.kopivad.quizzes.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.jooq.DSLContext;
import org.jooq.Record;
import org.jooq.RecordMapper;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.util.List;

import static com.kopivad.quizzes.domain.db.tables.Usr.USR;
import static org.apache.commons.lang3.math.NumberUtils.INTEGER_ZERO;

@Repository
@RequiredArgsConstructor
public class UserRepositoryImpl implements UserRepository {
    private final DSLContext dslContext;
    private final RoleRepository roleRepository;

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
        Long id = dslContext
                .insertInto(USR)
                .set(USR.NAME, user.getName())
                .set(USR.EMAIL, user.getEmail())
                .set(USR.PASSWORD, user.getPassword())
                .set(USR.CREATION_DATE, Timestamp.valueOf(user.getCreationDate()))
                .returning(USR.ID)
                .fetchOne()
                .getId();

        roleRepository.save(id, user.getRole());

        return id;
    }

    @Override
    public boolean update(User user) {
        int affectedRows = dslContext
                .update(USR)
                .set(USR.NAME, user.getName())
                .set(USR.EMAIL, user.getEmail())
                .set(USR.PASSWORD, user.getPassword())
                .where(USR.ID.eq(user.getId()))
                .execute();

        roleRepository.update(user.getId(), user.getRole());
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
                .role(roleRepository.findByUserId(record.getValue(USR.ID)))
                .creationDate(record.getValue(USR.CREATION_DATE).toLocalDateTime())
                .password(record.getValue(USR.PASSWORD))
                .build();
    }
}
