package com.kopivad.quizzes.utils;

import com.kopivad.quizzes.domain.Role;
import com.kopivad.quizzes.domain.User;
import com.kopivad.quizzes.domain.db.tables.records.UsersRecord;
import com.kopivad.quizzes.dto.UserDto;
import org.jooq.DSLContext;
import org.jooq.InsertReturningStep;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static com.kopivad.quizzes.domain.db.tables.Users.USERS;
import static org.apache.commons.lang3.math.NumberUtils.INTEGER_ZERO;

public class UserUtils {
    private final static DSLContext DSL_CONTEXT = TestUtils.createTestDefaultDSLContext();
    public final static Long TEST_USER_ID = 1L;


    public static List<User> generateUsers(int size) {
        return IntStream.range(INTEGER_ZERO, size)
                .mapToObj(i -> generateUser(i + TEST_USER_ID))
                .collect(Collectors.toUnmodifiableList());
    }

    public static User generateUser() {
        return new User(
                TEST_USER_ID,
                UUID.randomUUID().toString(),
                UUID.randomUUID().toString(),
                UUID.randomUUID().toString(),
                Role.USER,
                LocalDateTime.now()
        );
    }

    public static User generateUser(long id) {
        return new User(
                id,
                UUID.randomUUID().toString(),
                UUID.randomUUID().toString(),
                UUID.randomUUID().toString(),
                Role.USER,
                LocalDateTime.now()
        );
    }

    public static UserDto generateUserDto() {
        return new UserDto(
                UUID.randomUUID().toString(),
                UUID.randomUUID().toString(),
                UUID.randomUUID().toString()
        );
    }

    public static void deleteAll() {
        DSL_CONTEXT.deleteFrom(USERS).execute();
    }

    public static void insertRandomUsersInDb(int size) {
        List<UserDto> dtos = generateUserDtos(size);
        List<InsertReturningStep<UsersRecord>> values = dtos.stream().map(dto -> DSL_CONTEXT
                .insertInto(USERS, USERS.NAME, USERS.EMAIL, USERS.PASSWORD, USERS.ROLE, USERS.CREATION_DATE)
                .values(dto.getName(), dto.getEmail(), dto.getPassword(), Role.USER.name(), Timestamp.valueOf(LocalDateTime.now())))
                .collect(Collectors.toUnmodifiableList());

        DSL_CONTEXT.batch(values).execute();
    }

    public static long insertRandomUserInDb() {
        UserDto dto = generateUserDto();
        return DSL_CONTEXT
                .insertInto(USERS)
                .set(USERS.NAME, dto.getName())
                .set(USERS.EMAIL, dto.getEmail())
                .set(USERS.PASSWORD, dto.getPassword())
                .set(USERS.ROLE, Role.USER.name())
                .set(USERS.CREATION_DATE, Timestamp.valueOf(LocalDateTime.now()))
                .returning(USERS.ID)
                .fetchOne()
                .getId();
    }

    private static List<UserDto> generateUserDtos(int size) {
        return IntStream.range(INTEGER_ZERO, size)
                .mapToObj(i -> new UserDto(
                        UUID.randomUUID().toString(),
                        UUID.randomUUID().toString(),
                        UUID.randomUUID().toString()
                ))
                .collect(Collectors.toUnmodifiableList());
    }

    public static String insertRandomUserInDbWithEmail() {
        User dto = generateUser();
        return DSL_CONTEXT
                .insertInto(USERS)
                .set(USERS.NAME, dto.getName())
                .set(USERS.EMAIL, dto.getEmail())
                .set(USERS.PASSWORD, dto.getPassword())
                .set(USERS.ROLE, Role.USER.name())
                .set(USERS.CREATION_DATE, Timestamp.valueOf(LocalDateTime.now()))
                .returning(USERS.EMAIL)
                .fetchOne()
                .getEmail();
    }

    public static void insertRandomUsersInDbWithSimilarEmail(String emailPrefix, int size) {
        List<UserDto> dtos = generateUserDtos(size);
        List<InsertReturningStep<UsersRecord>> values = dtos.stream().map(dto -> DSL_CONTEXT
                .insertInto(USERS, USERS.NAME, USERS.EMAIL, USERS.PASSWORD, USERS.ROLE, USERS.CREATION_DATE)
                .values(dto.getName(), emailPrefix + dto.getEmail(), dto.getPassword(), Role.USER.name(), Timestamp.valueOf(LocalDateTime.now())))
                .collect(Collectors.toUnmodifiableList());

        DSL_CONTEXT.batch(values).execute();
    }

    public static void insertDefaultUser() {
        UserDto dto = generateUserDto();
        DSL_CONTEXT
                .insertInto(USERS)
                .set(USERS.ID, TEST_USER_ID)
                .set(USERS.NAME, dto.getName())
                .set(USERS.EMAIL, dto.getEmail())
                .set(USERS.PASSWORD, dto.getPassword())
                .set(USERS.ROLE, Role.USER.name())
                .set(USERS.CREATION_DATE, Timestamp.valueOf(LocalDateTime.now()))
                .onDuplicateKeyIgnore()
                .execute();
    }
}
