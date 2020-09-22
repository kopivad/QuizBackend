package com.kopivad.quizzes.repository.jooq;

import com.kopivad.quizzes.domain.User;
import com.kopivad.quizzes.dto.UserDto;
import com.kopivad.quizzes.repository.UserRepository;
import com.kopivad.quizzes.utils.TestUtils;
import com.kopivad.quizzes.utils.UserUtils;
import org.jooq.DSLContext;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class UserRepositoryImplTest {
    private final DSLContext dslContext = TestUtils.createTestDefaultDSLContext();
    private final UserRepository userRepository = new UserRepositoryImpl(dslContext);

    @BeforeEach
    void setUp() {
        UserUtils.deleteAll();
    }

    @Test
    void findAllTest() {
        int expected = 10;
        UserUtils.insertRandomUsersInDb(expected);
        List<User> actual = userRepository.findAll();

        assertThat(actual.size(), is(expected));
    }

    @Test
    void findByIdTest() {
        long id = UserUtils.insertRandomUserInDb();
        Optional<User> actual = userRepository.findById(id);

        assertThat(actual.isPresent(), is(true));
    }

    @Test
    void saveTest() {
        UserDto dto = UserUtils.generateUserDto();
        int expected = 1;
        int actual = userRepository.save(dto);

        assertThat(actual, is(expected));
    }

    @Test
    void updateTest() {
        long id = UserUtils.insertRandomUserInDb();
        User user = UserUtils.generateUser(id);
        int expected = 1;
        int actual = userRepository.update(user);

        assertThat(actual, is(expected));
    }

    @Test
    void deleteTest() {
        long id = UserUtils.insertRandomUserInDb();
        int expected = 1;
        int actual = userRepository.delete(id);

        assertThat(actual, is(expected));
    }

    @Test
    void findByEmailStartsWithTest() {
        String emailPrefix = "test";
        int expected = 10;
        UserUtils.insertRandomUsersInDbWithSimilarEmail(emailPrefix, expected);
        List<User> actual = userRepository.findByEmailStartsWith(emailPrefix);

        assertThat(actual.size(), is(expected));
    }

    @Test
    void findByEmailTest() {
        String expected = UserUtils.insertRandomUserInDbWithEmail();
        Optional<User> actual = userRepository.findByEmail(expected);

        assertThat(actual.isPresent(), is(true));
    }

    @Test
    void updatePassword() {
        long id = UserUtils.insertRandomUserInDb();
        String password = UUID.randomUUID().toString();
        int expected = 1;
        int actual = userRepository.updatePassword(id, password);

        assertThat(actual, is(expected));
    }
}