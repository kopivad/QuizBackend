package com.kopivad.quizzes.service.impl;

import com.kopivad.quizzes.domain.Group;
import com.kopivad.quizzes.dto.SaveGroupDto;
import com.kopivad.quizzes.repository.GroupRepository;
import com.kopivad.quizzes.service.GroupService;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.apache.commons.lang3.math.NumberUtils.INTEGER_ZERO;

@Service
@RequiredArgsConstructor
public class GroupServiceImpl implements GroupService {
    private final GroupRepository groupRepository;

    @Override
    public boolean save(SaveGroupDto dto) {
        String defaultJoinCode = UUID.randomUUID().toString();
        Group group = new Group(1L, dto.getName(), defaultJoinCode);
        long id = groupRepository.save(group);

        if (ObjectUtils.isNotEmpty(dto.getUsersIds()))
            dto.getUsersIds()
                    .forEach(u -> saveGroupForUser(id, u));

        if (ObjectUtils.isNotEmpty(dto.getQuizzesIds()))
            dto.getQuizzesIds()
                    .forEach(q -> saveGroupForQuiz(id, q));

        return id > INTEGER_ZERO;
    }

    @Override
    public boolean update(Group group) {
        return groupRepository.update(group);
    }

    @Override
    public List<Group> getAll() {
        return groupRepository.findAll();
    }

    @Override
    public Optional<Group> getById(long id) {
        return groupRepository.findById(id);
    }

    @Override
    public List<Group> getAllByUserId(long id) {
        return groupRepository.findAllByUserId(id);
    }

    public boolean saveGroupForUser(long id, long userId) {
        return groupRepository.saveGroupForUser(id, userId);
    }

    public boolean saveGroupForQuiz(long id, long quizId) {
        return groupRepository.saveGroupForQuiz(id, quizId);
    }
}
