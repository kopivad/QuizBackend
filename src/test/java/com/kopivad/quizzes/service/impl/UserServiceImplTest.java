package com.kopivad.quizzes.service.impl;

import com.kopivad.quizzes.domain.User;
import com.kopivad.quizzes.form.UserForm;
import com.kopivad.quizzes.repository.UserRepository;
import com.kopivad.quizzes.utils.FormUtils;
import com.kopivad.quizzes.utils.UserUtils;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import static org.apache.commons.lang3.math.NumberUtils.LONG_ONE;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class UserServiceImplTest {
    @InjectMocks
    private UserServiceImpl userService;
    @Mock
    private UserRepository userRepository;
    @Mock
    private PasswordEncoder passwordEncoder;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testGetAll() {
        int size = 10;
        List<User> expectedResult = UserUtils.generateUsers(size);
        when(userRepository.findAll()).thenReturn(expectedResult);
        List<User> actualResult = userService.getAll();
        assertThat(actualResult, is(expectedResult));
        verify(userRepository).findAll();
    }

    @Test
    public void testGetById() {
        User expectedResult = UserUtils.generateUser();
        when(userRepository.findById(eq(LONG_ONE))).thenReturn(expectedResult);
        User actualResult = userService.getById(LONG_ONE);
        assertThat(actualResult, is(expectedResult));
        verify(userRepository).findById(LONG_ONE);
    }

    @Test
    public void testSave() {
        UserForm userForSave = UserUtils.generateUserForm();
        User expectedResult = FormUtils.toUser(userForSave)
                .toBuilder()
                .creationDate(LocalDateTime.now())
                .password(String.valueOf(UUID.randomUUID()))
                .id(LONG_ONE)
                .build();
        when(passwordEncoder.encode(anyString())).thenReturn(String.valueOf(UUID.randomUUID()));
        when(userRepository.save(any())).thenReturn(expectedResult);
        User actualResult = userService.save(userForSave);
        assertThat(actualResult.getCreationDate(), notNullValue());
        assertThat(actualResult, is(expectedResult));
        verify(passwordEncoder).encode(anyString());
        verify(userRepository).save(any(User.class));
    }

    @Test
    public void testUpdate() {
        String dataForUpdate = "Name";
        UserForm userForUpdate = UserUtils.generateUserForm().toBuilder().name(dataForUpdate).build();
        User expectedResult = FormUtils.toUser(userForUpdate).toBuilder().id(LONG_ONE).build();
        when(passwordEncoder.encode(anyString())).thenReturn(String.valueOf(UUID.randomUUID()));
        when(userRepository.update(anyLong(), any())).thenReturn(expectedResult);
        User actualResult = userService.update(LONG_ONE, userForUpdate);
        assertThat(actualResult, is(expectedResult));
        assertThat(actualResult.getName(), is(expectedResult.getName()));
        verify(passwordEncoder).encode(anyString());
        verify(userRepository).update(eq(LONG_ONE), any());
    }

    @Test
    public void testDelete() {
        userService.delete(LONG_ONE);
        verify(userRepository).delete(LONG_ONE);
    }
}
