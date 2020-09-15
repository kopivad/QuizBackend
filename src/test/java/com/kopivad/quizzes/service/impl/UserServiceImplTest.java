package com.kopivad.quizzes.service.impl;

import com.kopivad.quizzes.domain.User;
import com.kopivad.quizzes.dto.UserDto;
import com.kopivad.quizzes.mapper.UserMapper;
import com.kopivad.quizzes.repository.UserRepository;
import com.kopivad.quizzes.utils.UserUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;
import java.util.UUID;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

@SpringBootTest
public class UserServiceImplTest {
    @InjectMocks
    private UserServiceImpl userService;
    @Mock
    private UserRepository userRepository;
    @Mock
    private PasswordEncoder passwordEncoder;
    @Mock
    private UserMapper mapper;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testGetAll() {
        int size = 10;
        List<User> expected = UserUtils.generateUsers(size);
        when(userRepository.findAll()).thenReturn(expected);
        when(mapper.toDto(any(User.class))).thenReturn(UserUtils.generateUserDto());
        List<UserDto> actual = userService.getAll();

        assertThat(actual.size(), is(expected.size()));

        verify(userRepository).findAll();
        verify(mapper, times(size)).toDto(any(User.class));
    }

    @Test
    public void testGetById() {
        User expectedResult = UserUtils.generateUser();
        when(userRepository.findById(anyLong())).thenReturn(expectedResult);
        User actualResult = userService.getById(expectedResult.getId());

        assertThat(actualResult, is(expectedResult));

        verify(userRepository).findById(anyLong());
    }

    @Test
    public void testSave() {
        UserDto expected = UserUtils.generateUserDto();
        when(mapper.toEntity(any(UserDto.class))).thenReturn(UserUtils.generateUser());
        when(passwordEncoder.encode(anyString())).thenReturn(String.valueOf(UUID.randomUUID()));
        when(userRepository.save(any())).thenReturn(expected.getId());
        long actual = userService.save(expected);

        assertThat(actual, is(expected.getId()));

        verify(passwordEncoder).encode(anyString());
        verify(userRepository).save(any(User.class));
        verify(mapper).toEntity(any(UserDto.class));
    }

    @Test
    public void testUpdate() {
        UserDto expected = UserUtils.generateUserDto();
        when(mapper.toEntity(any(UserDto.class))).thenReturn(UserUtils.generateUser());
        when(passwordEncoder.encode(anyString())).thenReturn(String.valueOf(UUID.randomUUID()));
        when(userRepository.update(any(User.class))).thenReturn(true);
        boolean actualResult = userService.update(expected);

        assertTrue(actualResult);

        verify(mapper).toEntity(any(UserDto.class));
        verify(passwordEncoder).encode(anyString());
        verify(userRepository).update(any(User.class));
    }

    @Test
    public void testDelete() {
        long expected = UserUtils.generateUser().getId();
        when(userRepository.delete(anyLong())).thenReturn(true);
        boolean actual = userRepository.delete(expected);

        assertTrue(actual);

        verify(userRepository).delete(anyLong());
    }
}
