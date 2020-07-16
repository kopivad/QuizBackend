package com.kopivad.quizzes.repository.jooq;

import com.kopivad.quizzes.domain.Role;
import com.kopivad.quizzes.repository.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.jooq.DSLContext;
import org.springframework.stereotype.Repository;

import static com.kopivad.quizzes.domain.db.tables.UsrRoles.USR_ROLES;
import static org.apache.commons.lang3.math.NumberUtils.INTEGER_ZERO;

@Repository
@RequiredArgsConstructor
public class RoleRepositoryImpl implements RoleRepository {
    private final DSLContext dslContext;

    @Override
    public long save(long userId, Role role) {
        return dslContext
                .insertInto(USR_ROLES)
                .set(USR_ROLES.USER_ID, userId)
                .set(USR_ROLES.ROLE, role.name())
                .returning(USR_ROLES.ID)
                .fetchOne()
                .getId();
    }

    @Override
    public boolean update(long userId, Role role) {
        int affectedRows = dslContext
                .update(USR_ROLES)
                .set(USR_ROLES.USER_ID, userId)
                .set(USR_ROLES.ROLE, role.name())
                .execute();
        return affectedRows > INTEGER_ZERO;
    }

    @Override
    public Role findByUserId(long userId) {
        return Role.valueOf(dslContext
                .selectFrom(USR_ROLES)
                .where(USR_ROLES.USER_ID.eq(userId))
                .fetchOne()
                .getRole());
    }
}
