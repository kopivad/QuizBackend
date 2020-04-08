package com.kopivad.quizzes.repository.jdbc;

import com.kopivad.quizzes.repository.utils.UserUtils;
import com.kopivad.quizzes.domain.User;
import com.kopivad.quizzes.repository.UserRepository;
import com.kopivad.quizzes.repository.utils.TestUtils;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import javax.sql.DataSource;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.Assert.*;

@RunWith(JUnit4.class)
public class UserRepositoryJdbcTest {
    private static UserRepository userRepository;

    @BeforeClass
    public static void init() {
        DataSource pgDataSource = TestUtils.createTestDefaultPgDataSource();
//        TestUtils.createUsersTableIfNotExists(pgDataSource);
        userRepository = new UserRepositoryJdbc(pgDataSource);
    }

    @Test
    public void findAllTest() {
        List<User> generatedAccounts = UserUtils.generateUsers(5);
        List<User> savedUsers = generatedAccounts
                .stream()
                .map(user -> userRepository.save(user))
                .collect(Collectors.toList());
        List<User> allUsers = userRepository.findAll();
        assertTrue(allUsers.containsAll(savedUsers));
    }

    @Test
    public void findByIdTest() {
        User generatedUser = UserUtils.generateUser();
        User savedUser = userRepository.save(generatedUser);
        User user = userRepository.findById(savedUser.getId());
        assertEquals(savedUser, user);
    }

    @Test
    public void saveTest() {
        User generatedUser = UserUtils.generateUser();
        int accountsCountBeforeInsert = userRepository.findAll().size();
        User user = userRepository.save(generatedUser);
        List<User> allUsers = userRepository.findAll();
        assertNotNull(user.getId());
        assertEquals(accountsCountBeforeInsert + 1, allUsers.size());
        assertTrue(allUsers.contains(user));
    }

    @Test
    public void updateTest() {
        String dataForUpdate = "some@email.com";
        User generatedUser = UserUtils.generateUser();
        User savedUser = userRepository.save(generatedUser);
        generatedUser.setEmail(dataForUpdate);
        User updatedUser = userRepository.update(savedUser.getId(), generatedUser);
        assertEquals(savedUser.getId(), updatedUser.getId());
        assertNotEquals(savedUser.getEmail(), updatedUser.getEmail());
    }

    @Test
    public void deleteTest() {
        User generatedUser = UserUtils.generateUser();
        User savedUser = userRepository.save(generatedUser);
        List<User> allUsers = userRepository.findAll();
        assertTrue(allUsers.contains(savedUser));
        userRepository.delete(savedUser.getId());
        assertFalse(userRepository.findAll().contains(savedUser));
    }

    @Test
    public void findByEmailTest() {
        User generatedUser = UserUtils.generateUser();
        User savedUser = userRepository.save(generatedUser);
        User user = userRepository.findByEmail(savedUser.getEmail());
        assertEquals(savedUser, user);
    }
}