package com.kopivad.quizzes.service;

import com.kopivad.quizzes.domain.Group;
import com.kopivad.quizzes.dto.GroupDto;

import java.util.List;

public interface GroupService {
    long create(GroupDto dto);

    boolean update(GroupDto dto);

    List<GroupDto> getAll();

    Group getById(long id);
}
