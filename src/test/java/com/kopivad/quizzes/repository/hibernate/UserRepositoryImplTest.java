package com.kopivad.quizzes.repository.hibernate;

import com.kopivad.quizzes.domain.User;
import com.kopivad.quizzes.domain.utils.UserUtils;
import com.kopivad.quizzes.repository.UserRepository;
import com.kopivad.quizzes.repository.utils.TestUtils;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import javax.persistence.EntityManagerFactory;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.Assert.*;

@RunWith(JUnit4.class)
public class UserRepositoryImplTest {
    private static UserRepository hibernateRepository;

    @BeforeClass
    public static void init() {
        EntityManagerFactory entityManagerFactory = TestUtils.createEntityManagerFactory("BasicUnit");
        hibernateRepository = new UserRepositoryImpl(entityManagerFactory.createEntityManager());
    }

    @Test
    public void findAllTest() {
        List<User> generatedAccounts = UserUtils.generateUsers(5);
        List<User> savedUsers = generatedAccounts
                .stream()
                .map(user -> hibernateRepository.save(user))
                .collect(Collectors.toList());
        List<User> allUsers = hibernateRepository.findAll();
        assertTrue(allUsers.containsAll(savedUsers));
    }

    @Test
    public void findByIdTest() {
        User generatedUser = UserUtils.generateUser();
        User savedUser = hibernateRepository.save(generatedUser);
        User user = hibernateRepository.findById(savedUser.getId());
        assertEquals(savedUser, user);
    }

    @Test
    public void saveTest() {
        User generatedUser = UserUtils.generateUser();
        int accountsCountBeforeInsert = hibernateRepository.findAll().size();
        User user = hibernateRepository.save(generatedUser);
        List<User> allUsers = hibernateRepository.findAll();
        assertNotNull(user.getId());
        assertEquals(accountsCountBeforeInsert + 1, allUsers.size());
        assertTrue(allUsers.contains(user));
    }

    @Test
    public void updateTest() {
        String dataForUpdate = "some@email.com";
        User generatedUser = UserUtils.generateUser();
        User savedUser = hibernateRepository.save(generatedUser);
        generatedUser.setEmail(dataForUpdate);
        User updatedUser = hibernateRepository.update(savedUser.getId(), generatedUser);
        assertEquals(savedUser.getId(), updatedUser.getId());
        assertNotEquals(savedUser.getEmail(), updatedUser.getEmail());
    }

    @Test
    public void deleteTest() {
        User generatedUser = UserUtils.generateUser();
        User savedUser = hibernateRepository.save(generatedUser);
        List<User> allUsers = hibernateRepository.findAll();
        assertTrue(allUsers.contains(savedUser));
        hibernateRepository.delete(savedUser.getId());
        assertFalse(hibernateRepository.findAll().contains(savedUser));
    }

    @Test
    public void findByEmailTest() {
        User generatedUser = UserUtils.generateUser();
        User savedUser = hibernateRepository.save(generatedUser);
        User user = hibernateRepository.findByEmail(savedUser.getEmail());
        assertEquals(savedUser, user);
    }

}