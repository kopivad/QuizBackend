package com.kopivad.quizzes.repository;

import com.kopivad.quizzes.domain.Group;
import com.kopivad.quizzes.dto.QuizDto;
import com.kopivad.quizzes.dto.UserDto;

import java.util.List;

public interface GroupRepository {
    long save(Group group);

    boolean update(Group group);

    List<Group> findAll();

    Group findById(long id);

    List<Group> findAllByUserId(long id);

    boolean saveGroupForUser(long id, UserDto userDto);

    boolean saveGroupForQuiz(long id, QuizDto quizDto);
}
