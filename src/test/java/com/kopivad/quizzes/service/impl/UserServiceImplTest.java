package com.kopivad.quizzes.service.impl;

import com.kopivad.quizzes.domain.Quiz;
import com.kopivad.quizzes.domain.User;
import com.kopivad.quizzes.repository.UserRepository;
import com.kopivad.quizzes.repository.utils.QuizUtils;
import com.kopivad.quizzes.repository.utils.UserUtils;
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
        List<User> userFromDB = UserUtils.generateUsers(10);
        when(userRepository.findAll()).thenReturn(userFromDB);
        List<User> users = userService.getAll();
        assertThat(users, equalTo(userFromDB));
        verify(userRepository, times(1)).findAll();
    }

    @Test
    public void getByIdTest() {
        User userFromDB = UserUtils.generateUser();
        List<Quiz> userQuizzes = QuizUtils.generateQuizzes(10);
        when(userRepository.findById(1L)).thenReturn(userFromDB);
        when(quizService.getAll()).thenReturn(userQuizzes);
        User user = userService.getById(1L);
        assertThat(user, equalTo(userFromDB));
        assertThat(user.getQuizzes(), equalTo(userQuizzes));
        verify(userRepository, times(1)).findById(1L);
        verify(quizService, times(1)).getAll();
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
        verify(passwordEncoder, times(1)).encode(anyString());
        verify(userRepository, times(1)).save(any());
    }

    @Test
    public void update() {
        User userForUpdate = UserUtils.generateUser();
        User userFromDB = UserUtils.generateUser();
        when(passwordEncoder.encode(anyString())).thenReturn(String.valueOf(UUID.randomUUID()));
        when(userRepository.update(anyLong(), any())).thenReturn(userFromDB);
        User updatedUser = userService.update(1L, userForUpdate);
        assertThat(userForUpdate.getId(), equalTo(userFromDB.getId()));
        assertThat(updatedUser, equalTo(userForUpdate));
        verify(passwordEncoder, times(1)).encode(anyString());
        verify(userRepository, times(1)).update(anyLong(), any());
    }

    @Test
    public void delete() {
        userService.delete(1l);
        verify(userRepository, times(1)).delete(anyLong());
    }
}
