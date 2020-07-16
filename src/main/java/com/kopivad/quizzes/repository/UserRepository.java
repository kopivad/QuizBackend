package com.kopivad.quizzes.repository;

import com.kopivad.quizzes.domain.User;

import java.util.List;

public interface UserRepository {
    List<User> findAll();
    User findById(Long id);
    long save(User user);
    boolean update(User user);
    boolean delete(Long id);
}
