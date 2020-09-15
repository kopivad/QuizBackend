package com.kopivad.quizzes.repository.jooq;

import com.kopivad.quizzes.domain.Role;
import com.kopivad.quizzes.domain.User;
import com.kopivad.quizzes.dto.UserDto;
import com.kopivad.quizzes.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.jooq.DSLContext;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static com.kopivad.quizzes.domain.db.tables.GroupsUsers.GROUPS_USERS;
import static com.kopivad.quizzes.domain.db.tables.Users.USERS;

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
    public int save(UserDto user) {
        return dslContext
                .insertInto(USERS)
                .set(USERS.NAME, user.getName())
                .set(USERS.EMAIL, user.getEmail())
                .set(USERS.PASSWORD, user.getPassword())
                .set(USERS.CREATION_DATE, Timestamp.valueOf(LocalDateTime.now()))
                .set(USERS.ROLE, Role.USER.name())
                .execute();
    }

    @Override
    public int update(User user) {
        return dslContext
                .update(USERS)
                .set(USERS.NAME, user.getName())
                .set(USERS.EMAIL, user.getEmail())
                .set(USERS.ROLE, user.getRole().name())
                .where(USERS.ID.eq(user.getId()))
                .execute();
    }

    @Override
    public int delete(Long id) {
        return dslContext
                .delete(USERS)
                .where(USERS.ID.eq(id))
                .execute();
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
                .select(GROUPS_USERS.USER_ID)
                .from(GROUPS_USERS)
                .where(GROUPS_USERS.GROUP_ID.eq(id))
                .fetchInto(Long.class);
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
    public int updatePassword(long id, String password) {
        return dslContext
                .update(USERS)
                .set(USERS.PASSWORD, password)
                .where(USERS.ID.eq(id))
                .execute();
    }
}
