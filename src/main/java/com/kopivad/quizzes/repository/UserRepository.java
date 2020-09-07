package com.kopivad.quizzes.repository;

import com.kopivad.quizzes.domain.User;

import java.util.List;

public interface UserRepository {
    List<User> findAll();

    User findById(Long id);

    long save(User user);

    boolean update(User user);

    boolean delete(Long id);

    List<User> findByGroupId(long id);

    List<User> findByEmailStartsWith(String email);

    User findByEmail(String email);

    boolean isUserExistsByEmail(String email);
}
