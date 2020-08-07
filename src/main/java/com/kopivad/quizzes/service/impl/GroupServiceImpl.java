package com.kopivad.quizzes.service.impl;

import com.kopivad.quizzes.domain.Group;
import com.kopivad.quizzes.domain.Quiz;
import com.kopivad.quizzes.domain.User;
import com.kopivad.quizzes.dto.GroupDto;
import com.kopivad.quizzes.dto.QuizDto;
import com.kopivad.quizzes.dto.UserDto;
import com.kopivad.quizzes.mapper.GroupMapper;
import com.kopivad.quizzes.mapper.QuizMapper;
import com.kopivad.quizzes.mapper.UserMapper;
import com.kopivad.quizzes.repository.GroupRepository;
import com.kopivad.quizzes.service.GroupService;
import com.kopivad.quizzes.service.QuizService;
import com.kopivad.quizzes.service.UserService;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class GroupServiceImpl implements GroupService {
    private final GroupRepository groupRepository;
    private final GroupMapper groupMapper;
    private final UserService userService;
    private final QuizService quizService;
    private final UserMapper userMapper;
    private final QuizMapper quizMapper;

    @Override
    public long create(GroupDto dto) {
        Group group = groupMapper.toEntity(dto);
        long id = groupRepository.save(group);

        if (ObjectUtils.isNotEmpty(dto.getUsers()))
            dto.getUsers()
                    .forEach(u -> userService.addGroup(u.getId(), id));

        if (ObjectUtils.isNotEmpty(dto.getQuizzes()))
            dto.getQuizzes()
                    .forEach(q -> quizService.addGroup(q.getId(), id));

        return id;
    }

    @Override
    public boolean update(GroupDto dto) {
        Group group = groupMapper.toEntity(dto);

        if (ObjectUtils.isNotEmpty(dto.getUsers()))
            dto.getUsers()
                    .forEach(u -> userService.addGroup(u.getId(), dto.getId()));

        if (ObjectUtils.isNotEmpty(dto.getQuizzes()))
            dto.getQuizzes()
                    .forEach(q -> quizService.addGroup(q.getId(), dto.getId()));

        return groupRepository.update(group);
    }

    @Override
    public List<GroupDto> getAll() {
        List<GroupDto> groups = groupRepository.findAll()
                .stream()
                .map(groupMapper::toDto)
                .collect(Collectors.toUnmodifiableList());

        return groups
                .stream()
                .map(getGroupDataFunction())
                .collect(Collectors.toUnmodifiableList());
    }

    private Function<GroupDto, GroupDto> getGroupDataFunction() {
        return g -> {
            List<UserDto> users = userMapper.allToDto(userService.getByGroupId(g.getId()));
            List<QuizDto> quizzes = quizMapper.allToDto(quizService.getByGroupId(g.getId()));
            return g.toBuilder()
                    .users(users)
                    .quizzes(quizzes)
                    .build();
        };
    }

    @Override
    public Group getById(long id) {
        Group group = groupRepository.findById(id);
        List<User> users = userService.getByGroupId(id);
        List<Quiz> quizzes = quizService.getByGroupId(id);
        return group.toBuilder().quizzes(quizzes).users(users).build();
    }
}
