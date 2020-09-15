package com.kopivad.quizzes.service;

import com.kopivad.quizzes.domain.User;
import com.kopivad.quizzes.dto.UserDto;

import java.util.List;
import java.util.Optional;

public interface UserService {
    List<User> getAll();

    Optional<User> getById(Long id);

    Optional<User> getByEmail(String email);

    boolean save(UserDto dto);

    boolean update(User user);

    boolean delete(Long id);

    List<User> getByEmailStartsWith(String email);

    boolean updatePassword(long userId, String password);
}
