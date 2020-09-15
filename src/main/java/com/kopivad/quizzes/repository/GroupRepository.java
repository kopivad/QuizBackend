package com.kopivad.quizzes.repository;

import com.kopivad.quizzes.domain.Group;

import java.util.List;
import java.util.Optional;

public interface GroupRepository {
    long save(Group group);

    boolean update(Group group);

    List<Group> findAll();

    Optional<Group> findById(long id);

    List<Group> findAllByUserId(long id);

    boolean saveGroupForUser(long id, long userId);

    boolean saveGroupForQuiz(long id, long quizId);
}
