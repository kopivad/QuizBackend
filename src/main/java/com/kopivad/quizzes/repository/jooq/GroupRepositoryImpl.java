package com.kopivad.quizzes.repository.jooq;

import com.kopivad.quizzes.domain.Group;
import com.kopivad.quizzes.domain.db.tables.records.GroupsRecord;
import com.kopivad.quizzes.repository.GroupRepository;
import lombok.RequiredArgsConstructor;
import org.jooq.DSLContext;
import org.jooq.Record;
import org.jooq.RecordMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.kopivad.quizzes.domain.db.tables.Groups.GROUPS;
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

    public RecordMapper<Record, Group> getGroupsRecordGroupRecordMapper() {
        return r -> Group
                .builder()
                .id(r.getValue(GROUPS.ID))
                .name(r.getValue(GROUPS.NAME))
                .build();
    }
}
