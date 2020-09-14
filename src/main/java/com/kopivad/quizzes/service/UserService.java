package com.kopivad.quizzes.service;

import com.kopivad.quizzes.domain.User;
import com.kopivad.quizzes.dto.RegisterUserDto;
import com.kopivad.quizzes.dto.SaveUserDto;

import java.util.List;
import java.util.Optional;

public interface UserService {
    List<User> getAll();

    Optional<User> getById(Long id);

    Optional<User> getByEmail(String email);

    boolean save(SaveUserDto dto);

    boolean register(RegisterUserDto dto);

    boolean update(User user);

    boolean delete(Long id);

    List<User> getByGroupId(long id);

    List<User> getByEmailStartsWith(String email);

    boolean updatePassword(long userId, String password);
}
