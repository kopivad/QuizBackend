package com.kopivad.quizzes.service.impl;

import com.kopivad.quizzes.domain.User;
import com.kopivad.quizzes.dto.UserDto;
import com.kopivad.quizzes.repository.UserRepository;
import com.kopivad.quizzes.service.UserService;
import com.kopivad.quizzes.utils.UserUtils;
import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.apache.commons.lang3.math.NumberUtils.INTEGER_ONE;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.*;

public class UserServiceImplTest {
    private final PasswordEncoder passwordEncoder = mock(PasswordEncoder.class);
    private final UserRepository userRepository = mock(UserRepository.class);
    private final UserService userService = new UserServiceImpl(userRepository, passwordEncoder);

    @Test
    public void getAllTest() {
        int size = 10;
        List<User> expected = UserUtils.generateUsers(size);
        when(userRepository.findAll()).thenReturn(expected);
        List<User> actual = userService.getAll();

        assertThat(actual.size(), is(expected.size()));

        verify(userRepository).findAll();
    }

    @Test
    public void getByIdTest() {
        User expected = UserUtils.generateUser();
        when(userRepository.findById(anyLong())).thenReturn(Optional.of(expected));
        Optional<User> actual = userService.getById(expected.getId());

        assertThat(actual.isPresent(), is(true));

        verify(userRepository).findById(anyLong());
    }

    @Test
    public void saveTest() {
        UserDto expected = UserUtils.generateUserDto();
        when(passwordEncoder.encode(anyString())).thenReturn(String.valueOf(UUID.randomUUID()));
        when(userRepository.save(any(UserDto.class))).thenReturn(INTEGER_ONE);
        boolean actual = userService.save(expected);

        assertThat(actual, is(true));

        verify(passwordEncoder).encode(anyString());
        verify(userRepository).save(any(UserDto.class));
    }

    @Test
    public void updateTest() {
        User expected = UserUtils.generateUser();
        when(userRepository.update(any(User.class))).thenReturn(INTEGER_ONE);
        boolean actual = userService.update(expected);

        assertThat(actual, is(true));

        verify(userRepository).update(any(User.class));
    }

    @Test
    public void deleteTest() {
        long id = UserUtils.generateUser().getId();
        when(userRepository.delete(anyLong())).thenReturn(INTEGER_ONE);
        int expected = 1;
        int actual = userRepository.delete(id);

        assertThat(actual, is(expected));

        verify(userRepository).delete(anyLong());
    }

    @Test
    public void getByEmailStartsWithTest() {
        int size = 10;
        List<User> expected = UserUtils.generateUsers(size);
        when(userRepository.findByEmailStartsWith(anyString())).thenReturn(expected);

        List<User> actual = userService.getByEmailStartsWith(UUID.randomUUID().toString());

        assertThat(actual.size(), is(expected.size()));

        verify(userRepository).findByEmailStartsWith(anyString());
    }

    @Test
    public void updatePasswordTest() {
        User expected = UserUtils.generateUser();
        when(userRepository.updatePassword(anyLong(), anyString())).thenReturn(INTEGER_ONE);

        boolean actual = userService.updatePassword(expected.getId(), UUID.randomUUID().toString());

        assertThat(actual, is(true));

        verify(userRepository).updatePassword(anyLong(), anyString());
    }

    @Test
    public void getByEmailTest() {
        User expected = UserUtils.generateUser();
        when(userRepository.findByEmail(anyString())).thenReturn(Optional.of(expected));

        Optional<User> actual = userService.getByEmail(UUID.randomUUID().toString());

        assertThat(actual.isPresent(), is(true));

        verify(userRepository).findByEmail(anyString());
    }
}
