package com.kopivad.quizzes.repository.jooq;

import com.kopivad.quizzes.domain.Group;
import com.kopivad.quizzes.domain.db.tables.records.GroupsUsersRecord;
import com.kopivad.quizzes.repository.GroupRepository;
import lombok.RequiredArgsConstructor;
import org.jooq.DSLContext;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.kopivad.quizzes.domain.db.tables.Groups.GROUPS;
import static com.kopivad.quizzes.domain.db.tables.GroupsQuizzes.GROUPS_QUIZZES;
import static com.kopivad.quizzes.domain.db.tables.GroupsUsers.GROUPS_USERS;
import static org.apache.commons.lang3.math.NumberUtils.INTEGER_ZERO;

@Repository
@RequiredArgsConstructor
public class GroupRepositoryImpl implements GroupRepository {
    private final DSLContext dslContext;

    @Override
    public long save(Group group) {
        return dslContext
                .insertInto(GROUPS)
                .set(GROUPS.NAME, group.getName())
                .set(GROUPS.JOIN_CODE, group.getJoinCode())
                .returning(GROUPS.ID)
                .fetchOne()
                .getId();
    }

    @Override
    public boolean update(Group group) {
        int affectedRows = dslContext
                .update(GROUPS)
                .set(GROUPS.NAME, group.getName())
                .set(GROUPS.JOIN_CODE, group.getJoinCode())
                .execute();

        return affectedRows > INTEGER_ZERO;
    }

    @Override
    public List<Group> findAll() {
        return dslContext
                .selectFrom(GROUPS)
                .fetchInto(Group.class);
    }

    @Override
    public Optional<Group> findById(long id) {
        return dslContext
                .selectFrom(GROUPS)
                .where(GROUPS.ID.eq(id))
                .fetchOptionalInto(Group.class);
    }

    @Override
    public List<Group> findAllByUserId(long id) {
        List<Long> ids = getGroupUsersIds(id);

        return dslContext
                .selectFrom(GROUPS)
                .where(GROUPS.ID.in(ids))
                .fetchInto(Group.class);
    }

    private List<Long> getGroupUsersIds(long userId) {
        return dslContext
                .selectFrom(GROUPS_USERS)
                .where(GROUPS_USERS.USER_ID.eq(userId))
                .fetch()
                .stream()
                .map(GroupsUsersRecord::getGroupId)
                .collect(Collectors.toUnmodifiableList());
    }

    @Override
    public boolean saveGroupForUser(long id, long userId) {
        int affectedRows = dslContext
                .insertInto(GROUPS_USERS)
                .set(GROUPS_USERS.USER_ID, userId)
                .set(GROUPS_USERS.GROUP_ID, id)
                .execute();

        return affectedRows > INTEGER_ZERO;
    }

    @Override
    public boolean saveGroupForQuiz(long id, long quizId) {
        int affectedRows = dslContext
                .insertInto(GROUPS_QUIZZES)
                .set(GROUPS_QUIZZES.QUIZ_ID, quizId)
                .set(GROUPS_QUIZZES.GROUP_ID, id)
                .execute();

        return affectedRows > INTEGER_ZERO;
    }
}
