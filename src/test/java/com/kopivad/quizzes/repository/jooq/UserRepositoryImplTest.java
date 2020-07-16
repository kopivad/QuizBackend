package com.kopivad.quizzes.repository.jooq;

import com.kopivad.quizzes.domain.User;
import com.kopivad.quizzes.repository.RoleRepository;
import com.kopivad.quizzes.repository.UserRepository;
import com.kopivad.quizzes.utils.TestUtils;
import com.kopivad.quizzes.utils.UserUtils;
import org.jooq.DSLContext;
import org.jooq.SQLDialect;
import org.jooq.impl.DSL;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.List;
import java.util.stream.Collectors;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

public class UserRepositoryImplTest {
    private static UserRepository userRepository;

    @BeforeClass
    public static void init() {
        DSLContext dslContext = DSL.using(TestUtils.createTestDefaultPgDataSource(), SQLDialect.POSTGRES);
        RoleRepository roleRepository = new RoleRepositoryImpl(dslContext);
        userRepository = new UserRepositoryImpl(dslContext, roleRepository);
    }

    @Test
    public void findAllTest() {
        List<User> generatedAccounts = UserUtils.generateUsers(5);
        List<User> expected = generatedAccounts
                .stream()
                .map(user -> user.toBuilder().id(userRepository.save(user)).build())
                .collect(Collectors.toUnmodifiableList());
        List<User> actual = userRepository.findAll();

        assertTrue(actual.containsAll(expected));
    }

    @Test
    public void findByIdTest() {
        User generatedUser = UserUtils.generateUser();
        long expected = userRepository.save(generatedUser);
        User actual = userRepository.findById(expected);

        assertThat(actual.getId(), is(expected));
    }

    @Test
    public void saveTest() {
        User generatedUser = UserUtils.generateUser();
        long actual = userRepository.save(generatedUser);

        assertThat(actual, notNullValue());
    }

    @Test
    public void updateTest() {
        User generatedUser = UserUtils.generateUser();
        User expectedResult = generatedUser.toBuilder().id(userRepository.save(generatedUser)).build();
        boolean actual = userRepository.update(expectedResult);

        assertTrue(actual);
    }

    @Test
    public void deleteTest() {
        User generatedUser = UserUtils.generateUser();
        long id = userRepository.save(generatedUser);
        boolean actual = userRepository.delete(id);

        assertTrue(actual);
    }
}