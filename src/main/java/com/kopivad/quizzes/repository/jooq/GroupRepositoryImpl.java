package com.kopivad.quizzes.repository.jooq;

import com.kopivad.quizzes.domain.Group;
import com.kopivad.quizzes.domain.db.tables.records.GroupsQuizzesRecord;
import com.kopivad.quizzes.domain.db.tables.records.GroupsUsersRecord;
import com.kopivad.quizzes.dto.GroupDto;
import com.kopivad.quizzes.repository.GroupRepository;
import lombok.RequiredArgsConstructor;
import org.jooq.DSLContext;
import org.jooq.InsertValuesStep2;
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
    public long save(GroupDto group) {
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
    public int saveGroupForQuizzes(long id, List<Long> quizzesIds) {
        return dslContext.batch(getInsertQuizzesValues(id, quizzesIds)).execute().length;
    }

    @Override
    public int saveGroupForUsers(long id, List<Long> usersIds) {
        return dslContext.batch(getInsertUsersValues(id, usersIds)).execute().length;
    }

    private List<InsertValuesStep2<GroupsQuizzesRecord, Long, Long>> getInsertQuizzesValues(long id, List<Long> quizzesIds) {
        return quizzesIds.stream()
        .map(quizId -> dslContext
                .insertInto(GROUPS_QUIZZES, GROUPS_QUIZZES.GROUP_ID,GROUPS_QUIZZES.QUIZ_ID)
                .values(id, quizId))
        .collect(Collectors.toUnmodifiableList());
    }

    private List<InsertValuesStep2<GroupsUsersRecord, Long, Long>> getInsertUsersValues(long id, List<Long> usersIds) {
        return usersIds.stream()
                .map(userId -> dslContext
                        .insertInto(GROUPS_USERS, GROUPS_USERS.GROUP_ID,GROUPS_USERS.USER_ID)
                        .values(id, userId))
                .collect(Collectors.toUnmodifiableList());
    }
}
