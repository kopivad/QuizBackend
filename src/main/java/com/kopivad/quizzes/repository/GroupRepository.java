package com.kopivad.quizzes.repository;

import com.kopivad.quizzes.domain.Group;
import liquibase.license.LicenseService;

import java.util.List;

public interface GroupRepository {
    long save(Group group);

    boolean update(Group group);

    List<Group> findAll();

    Group findById(long id);
}
