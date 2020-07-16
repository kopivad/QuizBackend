package com.kopivad.quizzes.repository;

import com.kopivad.quizzes.domain.Role;

public interface RoleRepository {
    long save(long userId, Role role);
    boolean update(long userId, Role role);
    Role findByUserId(long userId);
}
