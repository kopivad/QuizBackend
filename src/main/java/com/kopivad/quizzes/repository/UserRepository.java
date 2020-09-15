package com.kopivad.quizzes.repository;

import com.kopivad.quizzes.domain.User;
import com.kopivad.quizzes.dto.UserDto;

import java.util.List;
import java.util.Optional;

public interface UserRepository {
    List<User> findAll();

    Optional<User> findById(Long id);

    int save(UserDto user);

    int update(User user);

    int delete(Long id);

    List<User> findByGroupId(long id);

    List<User> findByEmailStartsWith(String email);

    Optional<User> findByEmail(String email);

    int updatePassword(long id, String password);
}
