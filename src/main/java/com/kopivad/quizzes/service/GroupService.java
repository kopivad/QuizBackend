package com.kopivad.quizzes.service;

import com.kopivad.quizzes.domain.Group;
import com.kopivad.quizzes.dto.GroupDto;

import java.util.List;
import java.util.Optional;

public interface GroupService {
    boolean save(GroupDto dto);

    boolean update(Group group);

    List<Group> getAll();

    Optional<Group> getById(long id);

    List<Group> getAllByUserId(long id);
}
