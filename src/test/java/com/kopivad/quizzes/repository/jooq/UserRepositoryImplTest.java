package com.kopivad.quizzes.repository.jooq;

import com.kopivad.quizzes.domain.User;
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

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

public class UserRepositoryImplTest {
    private static DSLContext dslContext;
    private static UserRepository userRepository;


    @BeforeClass
    public static void init() {
        dslContext = DSL.using(TestUtils.createTestDefaultPgDataSource(), SQLDialect.POSTGRES);
        userRepository = new UserRepositoryImpl(dslContext);
    }

    @Test
    public void findAllTest() {
        List<User> generatedAccounts = UserUtils.generateUsers(5);
        List<User> savedUsers = generatedAccounts
                .stream()
                .map(user -> userRepository.save(user))
                .collect(Collectors.toUnmodifiableList());
        List<User> allUsers = userRepository.findAll();

        assertThat(savedUsers, notNullValue());
        assertThat(allUsers, notNullValue());
        assertTrue(allUsers.containsAll(savedUsers));
    }

    @Test
    public void findByIdTest() {
        User generatedUser = UserUtils.generateUser();
        User savedUser = userRepository.save(generatedUser);
        User user = userRepository.findById(savedUser.getId());

        assertThat(savedUser, notNullValue());
        assertThat(user, notNullValue());
        assertThat(savedUser, equalTo(user));
    }

    @Test
    public void saveTest() {
        User generatedUser = UserUtils.generateUser();
        int accountsCountBeforeInsert = userRepository.findAll().size();
        User user = userRepository.save(generatedUser);
        List<User> allUsers = userRepository.findAll();

        assertThat(user.getId(), notNullValue());
        assertThat(accountsCountBeforeInsert + 1, equalTo(allUsers.size()));
        assertThat(allUsers, hasItem(user));
    }

    @Test
    public void updateTest() {
        String dataForUpdate = "some@email.com";
        User generatedUser = UserUtils.generateUser();
        User savedUser = userRepository.save(generatedUser);
        User userForUpdate = generatedUser.toBuilder().email(dataForUpdate).build();
        User updatedUser = userRepository.update(savedUser.getId(), userForUpdate);


        assertThat(savedUser, notNullValue());
        assertThat(updatedUser, notNullValue());
        assertThat(savedUser.getId(), is(updatedUser.getId()));
        assertThat(savedUser.getEmail(), not(equalTo(updatedUser.getEmail())));
    }

    @Test
    public void deleteTest() {
        User generatedUser = UserUtils.generateUser();
        User savedUser = userRepository.save(generatedUser);
        List<User> allUsersBeforeDeleting = userRepository.findAll();
        userRepository.delete(savedUser.getId());
        List<User> allUsersAfterDeleting = userRepository.findAll();

        assertThat(savedUser, notNullValue());
        assertThat(allUsersBeforeDeleting, notNullValue());
        assertThat(allUsersAfterDeleting, notNullValue());
        assertThat(allUsersBeforeDeleting, hasItem(savedUser));
        assertThat(allUsersAfterDeleting, not(hasItem(savedUser)));
    }

    @Test
    public void findByEmailTest() {
        User generatedUser = UserUtils.generateUser();
        User savedUser = userRepository.save(generatedUser);
        User user = userRepository.findByEmail(savedUser.getEmail());

        assertThat(savedUser, notNullValue());
        assertThat(user, notNullValue());
        assertThat(savedUser, equalTo(user));
    }
}