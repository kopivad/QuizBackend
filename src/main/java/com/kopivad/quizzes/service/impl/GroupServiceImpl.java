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
import java.util.UUID;
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
    public long save(GroupDto dto) {
        Group group = groupMapper.toEntity(dto);
        String defaultJoinCode = UUID.randomUUID().toString();
        Group groupWithJoinCode = group
                .toBuilder()
                .joinCode(defaultJoinCode)
                .build();

        long id = groupRepository.save(groupWithJoinCode);

        if (ObjectUtils.isNotEmpty(dto.getUsers()))
            dto.getUsers()
                    .forEach(u -> addGroupForUser(id, u));

        if (ObjectUtils.isNotEmpty(dto.getQuizzes()))
            dto.getQuizzes()
                    .forEach(q -> addGroupForQuiz(id, q));

        return id;
    }

    @Override
    public boolean update(GroupDto dto) {
        Group group = groupMapper.toEntity(dto);
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

    @Override
    public List<GroupDto> getAllByUserId(long id) {
        List<Group> groups = groupRepository.findAllByUserId(id);
        return groups
                .stream()
                .map(groupMapper::toDto)
                .collect(Collectors.toUnmodifiableList());
    }

    public boolean addGroupForUser(long id, UserDto userDto) {
        return groupRepository.saveGroupForUser(id, userDto);
    }

    public boolean addGroupForQuiz(long id, QuizDto quizDto) {
        return groupRepository.saveGroupForQuiz(id, quizDto);
    }
}
