package com.kopivad.quizzes.utils;

import com.kopivad.quizzes.domain.Group;
import com.kopivad.quizzes.domain.db.tables.records.GroupsQuizzesRecord;
import com.kopivad.quizzes.domain.db.tables.records.GroupsRecord;
import com.kopivad.quizzes.domain.db.tables.records.GroupsUsersRecord;
import com.kopivad.quizzes.dto.GroupDto;
import org.jooq.DSLContext;
import org.jooq.InsertReturningStep;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static com.kopivad.quizzes.domain.db.tables.Groups.GROUPS;
import static com.kopivad.quizzes.domain.db.tables.GroupsQuizzes.GROUPS_QUIZZES;
import static com.kopivad.quizzes.domain.db.tables.GroupsUsers.GROUPS_USERS;
import static org.apache.commons.lang3.math.NumberUtils.INTEGER_ZERO;
import static org.apache.commons.lang3.math.NumberUtils.LONG_ONE;

public class GroupUtils {
    private final static DSLContext DSL_CONTEXT = TestUtils.createTestDefaultDSLContext();
    public final static Long TEST_GROUP_ID = 1L;

    public static long insertRandomGroup() {
        GroupDto dto = generateGroupDto();
        long id = DSL_CONTEXT
                .insertInto(GROUPS, GROUPS.NAME, GROUPS.JOIN_CODE)
                .values(dto.getName(), dto.getJoinCode())
                .returning(GROUPS.ID)
                .fetchOne()
                .getId();

        insertQuizzesWithGroup(dto, id);
        insertUsersWithGroup(dto, id);
        return id;
    }

    public static List<GroupDto> generateGroupDtos(int size) {
        return IntStream
                .range(INTEGER_ZERO, size)
                .mapToObj(i -> generateGroupDto())
                .collect(Collectors.toUnmodifiableList());
    }

    public static void insertRandomGroups(int size) {
        List<GroupDto> dtos = generateGroupDtos(size);
        List<InsertReturningStep<GroupsRecord>> values = dtos
                .stream()
                .map(dto -> DSL_CONTEXT
                        .insertInto(GROUPS, GROUPS.NAME, GROUPS.JOIN_CODE)
                        .values(dto.getName(), dto.getJoinCode()))
                .collect(Collectors.toUnmodifiableList());

        DSL_CONTEXT.batch(values).execute();
    }

    private static void insertQuizzesWithGroup(GroupDto dto, long id) {
        DSL_CONTEXT.batch(getQuizIdsValues(dto, id)).execute();
    }

    private static void insertUsersWithGroup(GroupDto dto, long id) {
        DSL_CONTEXT.batch(getUsersValues(dto, id)).execute();
    }


    private static List<InsertReturningStep<GroupsQuizzesRecord>> getQuizIdsValues(GroupDto dto, long id) {
        return dto
                .getQuizzesIds()
                .stream()
                .map(quizId ->
                        DSL_CONTEXT
                                .insertInto(GROUPS_QUIZZES, GROUPS_QUIZZES.GROUP_ID, GROUPS_QUIZZES.QUIZ_ID)
                                .values(id, quizId))
                .collect(Collectors.toUnmodifiableList());
    }

    private static List<InsertReturningStep<GroupsUsersRecord>> getUsersValues(GroupDto dto, long id) {
        return dto
                .getUsersIds()
                .stream()
                .map(userId ->
                        DSL_CONTEXT
                                .insertInto(GROUPS_USERS, GROUPS_USERS.GROUP_ID, GROUPS_USERS.USER_ID)
                                .values(id, userId))
                .collect(Collectors.toUnmodifiableList());
    }

    public static GroupDto generateGroupDto() {
        return new GroupDto(
                UUID.randomUUID().toString(),
                UUID.randomUUID().toString(),
                List.of(UserUtils.TEST_USER_ID),
                List.of(QuizUtils.TEST_QUIZ_ID)
        );
    }

    public static Group generateGroup(long id) {
        return new Group(
                id,
                UUID.randomUUID().toString(),
                UUID.randomUUID().toString()
        );
    }

    public static Group generateGroup() {
        return new Group(
                TEST_GROUP_ID,
                UUID.randomUUID().toString(),
                UUID.randomUUID().toString()
        );
    }

    public static void deleteAll() {
        DSL_CONTEXT.deleteFrom(GROUPS).execute();
    }

    public static List<Group> generateGroups(int size) {
        return IntStream
                .range(INTEGER_ZERO, size)
                .mapToObj(i -> generateGroup(i + LONG_ONE))
                .collect(Collectors.toUnmodifiableList());
    }
}
