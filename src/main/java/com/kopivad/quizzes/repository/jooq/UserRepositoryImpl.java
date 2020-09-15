package com.kopivad.quizzes.repository.jooq;

import com.kopivad.quizzes.domain.User;
import com.kopivad.quizzes.domain.db.tables.records.GroupsUsersRecord;
import com.kopivad.quizzes.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.jooq.DSLContext;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.kopivad.quizzes.domain.db.tables.GroupsUsers.GROUPS_USERS;
import static com.kopivad.quizzes.domain.db.tables.Users.USERS;
import static org.apache.commons.lang3.math.NumberUtils.INTEGER_ZERO;

@Repository
@RequiredArgsConstructor
public class UserRepositoryImpl implements UserRepository {
    private final DSLContext dslContext;

    @Override
    public List<User> findAll() {
        return dslContext
                .selectFrom(USERS)
                .fetchInto(User.class);
    }

    @Override
    public Optional<User> findById(Long id) {
        return dslContext
                .selectFrom(USERS)
                .where(USERS.ID.eq(id))
                .fetchOptionalInto(User.class);
    }

    @Override
    public boolean save(User user) {
        int affectedRows = dslContext
                .insertInto(USERS)
                .set(USERS.NAME, user.getName())
                .set(USERS.EMAIL, user.getEmail())
                .set(USERS.PASSWORD, user.getPassword())
                .set(USERS.CREATION_DATE, Timestamp.valueOf(user.getCreationDate()))
                .set(USERS.ROLE, user.getRole().name())
                .execute();

        return affectedRows > INTEGER_ZERO;
    }

    @Override
    public boolean update(User user) {
        int affectedRows = dslContext
                .update(USERS)
                .set(USERS.NAME, user.getName())
                .set(USERS.EMAIL, user.getEmail())
                .set(USERS.ROLE, user.getRole().name())
                .where(USERS.ID.eq(user.getId()))
                .execute();

        return affectedRows > INTEGER_ZERO;
    }

    @Override
    public boolean delete(Long id) {
        int affectedRows = dslContext
                .delete(USERS)
                .where(USERS.ID.eq(id))
                .execute();

        return affectedRows > INTEGER_ZERO;
    }

    @Override
    public List<User> findByGroupId(long id) {
        List<Long> ids = getUserGroupsIds(id);

        return dslContext
                .selectFrom(USERS)
                .where(USERS.ID.in(ids))
                .fetchInto(User.class);
    }

    private List<Long> getUserGroupsIds(long id) {
        return dslContext
                .selectFrom(GROUPS_USERS)
                .where(GROUPS_USERS.GROUP_ID.eq(id))
                .fetch()
                .stream()
                .map(GroupsUsersRecord::getUserId)
                .collect(Collectors.toUnmodifiableList());
    }

    @Override
    public List<User> findByEmailStartsWith(String email) {
        return dslContext
                .selectFrom(USERS)
                .where(USERS.EMAIL.startsWithIgnoreCase(email))
                .fetchInto(User.class);
    }

    @Override
    public Optional<User> findByEmail(String email) {
        return dslContext
                .selectFrom(USERS)
                .where(USERS.EMAIL.eq(email))
                .fetchOptionalInto(User.class);
    }

    @Override
    public boolean updatePassword(long id, String password) {
        int affectedRows = dslContext
                .update(USERS)
                .set(USERS.PASSWORD, password)
                .where(USERS.ID.eq(id))
                .execute();

        return affectedRows > INTEGER_ZERO;
    }
}
