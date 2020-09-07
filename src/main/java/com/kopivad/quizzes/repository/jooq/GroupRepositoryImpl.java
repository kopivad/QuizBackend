package com.kopivad.quizzes.repository.jooq;

import com.kopivad.quizzes.domain.Group;
import com.kopivad.quizzes.domain.db.tables.QuizzesGroups;
import com.kopivad.quizzes.domain.db.tables.records.UsrsGroupsRecord;
import com.kopivad.quizzes.dto.QuizDto;
import com.kopivad.quizzes.dto.UserDto;
import com.kopivad.quizzes.repository.GroupRepository;
import lombok.RequiredArgsConstructor;
import org.jooq.DSLContext;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.stream.Collectors;

import static com.kopivad.quizzes.domain.db.tables.Groups.GROUPS;
import static com.kopivad.quizzes.domain.db.tables.QuizzesGroups.*;
import static com.kopivad.quizzes.domain.db.tables.UsrsGroups.USRS_GROUPS;
import static com.kopivad.quizzes.repository.jooq.RecordMappers.getGroupsRecordGroupRecordMapper;
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
                .returning(GROUPS.ID)
                .fetchOne()
                .getId();
    }

    @Override
    public boolean update(Group group) {
        int affectedRows = dslContext
                .update(GROUPS)
                .set(GROUPS.NAME, group.getName())
                .execute();

        return affectedRows > INTEGER_ZERO;
    }

    @Override
    public List<Group> findAll() {
        return dslContext
                .selectFrom(GROUPS)
                .fetch()
                .map(getGroupsRecordGroupRecordMapper());
    }

    @Override
    public Group findById(long id) {
        return dslContext
                .selectFrom(GROUPS)
                .where(GROUPS.ID.eq(id))
                .fetchOne()
                .map(getGroupsRecordGroupRecordMapper());
    }

    @Override
    public List<Group> findAllByUserId(long id) {
        List<Long> ids = dslContext
                .selectFrom(USRS_GROUPS)
                .where(USRS_GROUPS.USER_ID.eq(id))
                .fetch()
                .stream()
                .map(UsrsGroupsRecord::getGroupId)
                .collect(Collectors.toUnmodifiableList());

        return dslContext
                .selectFrom(GROUPS)
                .where(GROUPS.ID.in(ids))
                .fetch()
                .map(getGroupsRecordGroupRecordMapper());
    }

    @Override
    public boolean saveGroupForUser(long id, UserDto userDto) {
        int affectedRows = dslContext
                .insertInto(USRS_GROUPS)
                .set(USRS_GROUPS.USER_ID, userDto.getId())
                .set(USRS_GROUPS.GROUP_ID, id)
                .execute();

        return affectedRows > INTEGER_ZERO;
    }

    @Override
    public boolean saveGroupForQuiz(long id, QuizDto quizDto) {
        int affectedRows = dslContext
                .insertInto(QUIZZES_GROUPS)
                .set(QUIZZES_GROUPS.QUIZ_ID, quizDto.getId())
                .set(QUIZZES_GROUPS.GROUP_ID, id)
                .execute();

        return affectedRows > INTEGER_ZERO;
    }
}
