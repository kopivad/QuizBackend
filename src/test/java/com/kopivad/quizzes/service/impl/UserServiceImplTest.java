package com.kopivad.quizzes.service.impl;

import com.kopivad.quizzes.domain.Quiz;
import com.kopivad.quizzes.domain.User;
import com.kopivad.quizzes.repository.UserRepository;
import com.kopivad.quizzes.utils.QuizUtils;
import com.kopivad.quizzes.utils.UserUtils;
import com.kopivad.quizzes.service.QuizService;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;
import java.util.UUID;

import static org.apache.commons.lang3.math.NumberUtils.LONG_ONE;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class UserServiceImplTest {
    @InjectMocks
    private UserServiceImpl userService;
    @Mock
    private UserRepository userRepository;
    @Mock
    private QuizService quizService;
    @Mock
    private PasswordEncoder passwordEncoder;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void getAllTest() {
        int size = 10;
        List<User> userFromDB = UserUtils.generateUsers(size);
        when(userRepository.findAll()).thenReturn(userFromDB);
        List<User> users = userService.getAll();
        assertThat(users, equalTo(userFromDB));
        verify(userRepository).findAll();
    }

    @Test
    public void getByIdTest() {
        int size = 10;
        User userFromDB = UserUtils.generateUser();
        List<Quiz> userQuizzes = QuizUtils.generateQuizzes(size);
        when(userRepository.findById(LONG_ONE)).thenReturn(userFromDB);
        when(quizService.getAll()).thenReturn(userQuizzes);
        User user = userService.getById(LONG_ONE);
        assertThat(user, equalTo(userFromDB));
        assertThat(user.getQuizzes(), equalTo(userQuizzes));
        verify(userRepository).findById(LONG_ONE);
        verify(quizService).getAll();
    }

    @Test
    public void saveTest() {
        User userForSave = UserUtils.generateUser();
        User userFromDB = UserUtils.generateUser();
        when(passwordEncoder.encode(anyString())).thenReturn(String.valueOf(UUID.randomUUID()));
        when(userRepository.save(any())).thenReturn(userFromDB);
        User savedUser = userService.save(userForSave);
        assertThat(savedUser.getCreationDate(), notNullValue());
        assertThat(savedUser.getPassword(), not(equalTo(userForSave.getPassword())));
        verify(passwordEncoder).encode(anyString());
        verify(userRepository).save(any(User.class));
    }

    @Test
    public void update() {
        User userForUpdate = UserUtils.generateUser();
        User userFromDB = UserUtils.generateUser();
        when(passwordEncoder.encode(anyString())).thenReturn(String.valueOf(UUID.randomUUID()));
        when(userRepository.update(anyLong(), any())).thenReturn(userFromDB);
        User updatedUser = userService.update(LONG_ONE, userForUpdate);
        assertThat(userForUpdate.getId(), equalTo(userFromDB.getId()));
        assertThat(updatedUser, equalTo(userForUpdate));
        verify(passwordEncoder).encode(anyString());
        verify(userRepository).update(LONG_ONE, any());
    }

    @Test
    public void delete() {
        userService.delete(LONG_ONE);
        verify(userRepository).delete(LONG_ONE);
    }
}
