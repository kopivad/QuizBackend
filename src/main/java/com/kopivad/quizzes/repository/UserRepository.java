package com.kopivad.quizzes.repository;

import com.kopivad.quizzes.domain.User;

import java.util.List;
import java.util.Optional;

public interface UserRepository {
    List<User> findAll();

    Optional<User> findById(Long id);

    boolean save(User user);

    boolean update(User user);

    boolean delete(Long id);

    List<User> findByGroupId(long id);

    List<User> findByEmailStartsWith(String email);

    Optional<User> findByEmail(String email);

    boolean updatePassword(long id, String password);
}
