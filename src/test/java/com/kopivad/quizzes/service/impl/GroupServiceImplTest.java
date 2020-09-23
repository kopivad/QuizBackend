package com.kopivad.quizzes.service.impl;

import com.kopivad.quizzes.domain.Group;
import com.kopivad.quizzes.dto.GroupDto;
import com.kopivad.quizzes.repository.GroupRepository;
import com.kopivad.quizzes.service.GroupService;
import com.kopivad.quizzes.utils.GroupUtils;
import com.kopivad.quizzes.utils.UserUtils;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.apache.commons.lang3.math.NumberUtils.INTEGER_ONE;
import static org.apache.commons.lang3.math.NumberUtils.LONG_ONE;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.*;

public class GroupServiceImplTest {
    private final GroupRepository groupRepository = mock(GroupRepository.class);
    private final GroupService groupService = new GroupServiceImpl(groupRepository);

    @Test
    void saveTest() {
        GroupDto dto = GroupUtils.generateGroupDto();
        when(groupRepository.save(any(GroupDto.class))).thenReturn(LONG_ONE);
        when(groupRepository.saveGroupForQuizzes(anyLong(), anyList())).thenReturn(dto.getQuizzesIds().size());
        when(groupRepository.saveGroupForUsers(anyLong(), anyList())).thenReturn(dto.getUsersIds().size());

        boolean actual = groupService.save(dto);

        assertThat(actual, is(true));

        verify(groupRepository).save(any(GroupDto.class));
        verify(groupRepository).saveGroupForQuizzes(anyLong(), anyList());
        verify(groupRepository).saveGroupForUsers(anyLong(), anyList());
    }

    @Test
    void updateTest() {
        Group group = GroupUtils.generateGroup();
        when(groupRepository.update(any(Group.class))).thenReturn(INTEGER_ONE);

        boolean actual = groupService.update(group);

        assertThat(actual, is(true));

        verify(groupRepository).update(any(Group.class));
    }

    @Test
    void getAllTest() {
        int expected = 10;
        List<Group> groups = GroupUtils.generateGroups(expected);
        when(groupRepository.findAll()).thenReturn(groups);

        List<Group> actual = groupService.getAll();

        assertThat(actual.size(), is(expected));

        verify(groupRepository).findAll();
    }

    @Test
    void getByIdTest() {
        Group group = GroupUtils.generateGroup();
        when(groupRepository.findById(anyLong())).thenReturn(Optional.of(group));

        Optional<Group> actual = groupService.getById(GroupUtils.TEST_GROUP_ID);

        assertThat(actual.isPresent(), is(true));

        verify(groupRepository).findById(anyLong());
    }

    @Test
    void getAllByUserIdTest() {
        int expected = 10;
        List<Group> groups = GroupUtils.generateGroups(expected);
        when(groupRepository.findAllByUserId(anyLong())).thenReturn(groups);

        List<Group> actual = groupService.getAllByUserId(UserUtils.TEST_USER_ID);

        assertThat(actual.size(), is(expected));

        verify(groupRepository).findAllByUserId(anyLong());
    }
}
