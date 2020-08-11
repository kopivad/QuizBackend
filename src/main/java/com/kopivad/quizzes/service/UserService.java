package com.kopivad.quizzes.service;

import com.kopivad.quizzes.domain.User;
import com.kopivad.quizzes.dto.UserDto;

import java.util.List;

public interface UserService {
    List<UserDto> getAll();

    User getById(Long id);

    long save(UserDto userDto);

    boolean update(UserDto userDto);

    boolean delete(Long id);

    boolean addGroup(long userId, long groupId);

    List<User> getByGroupId(long id);

    List<UserDto> getByEmailStartsWith(String email);
}
