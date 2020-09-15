package com.kopivad.quizzes.repository;

import com.kopivad.quizzes.domain.Group;
import com.kopivad.quizzes.dto.GroupDto;

import java.util.List;
import java.util.Optional;

public interface GroupRepository {
    long save(GroupDto group);

    boolean update(Group group);

    List<Group> findAll();

    Optional<Group> findById(long id);

    List<Group> findAllByUserId(long id);

    int saveGroupForQuizzes(long id, List<Long> quizzesIds);

    int saveGroupForUsers(long id, List<Long> usersIds);
}
