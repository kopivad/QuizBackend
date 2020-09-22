package com.kopivad.quizzes.service.impl;

import com.kopivad.quizzes.domain.Group;
import com.kopivad.quizzes.dto.GroupDto;
import com.kopivad.quizzes.repository.GroupRepository;
import com.kopivad.quizzes.service.GroupService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static org.apache.commons.lang3.math.NumberUtils.INTEGER_ONE;

@Service
@RequiredArgsConstructor
public class GroupServiceImpl implements GroupService {
    private final GroupRepository groupRepository;

    @Override
    public boolean save(GroupDto dto) {
        long id = groupRepository.save(dto);
        int affectedQuizzesRows = groupRepository.saveGroupForQuizzes(id, dto.getQuizzesIds());
        int affectedUsersRows = groupRepository.saveGroupForUsers(id, dto.getUsersIds());

        return affectedQuizzesRows == dto.getQuizzesIds().size() && affectedUsersRows == dto.getUsersIds().size();
    }

    @Override
    public boolean update(Group group) {
        int affectedRows = groupRepository.update(group);
        return affectedRows == INTEGER_ONE;
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
}
