package com.kopivad.quizzes.repository.jooq;

import com.kopivad.quizzes.domain.Group;
import com.kopivad.quizzes.dto.GroupDto;
import com.kopivad.quizzes.repository.GroupRepository;
import com.kopivad.quizzes.utils.GroupUtils;
import com.kopivad.quizzes.utils.QuizUtils;
import com.kopivad.quizzes.utils.TestUtils;
import com.kopivad.quizzes.utils.UserUtils;
import org.jooq.DSLContext;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;

class GroupRepositoryImplTest {
    private final DSLContext dslContext = TestUtils.createTestDefaultDSLContext();
    private final GroupRepository groupRepository = new GroupRepositoryImpl(dslContext);

    @BeforeAll
    static void init() {
        UserUtils.insertDefaultUser();
        QuizUtils.insertDefaultQuiz();
    }

    @BeforeEach
    void setUp() {
        GroupUtils.deleteAll();
    }

    @Test
    void saveTest() {
        GroupDto dto = GroupUtils.generateGroupDto();
        long actual = groupRepository.save(dto);

        assertThat(actual, notNullValue());
    }

    @Test
    void updateTest() {
        long id = GroupUtils.insertRandomGroup();
        Group group = GroupUtils.generateGroup(id);
        int expected = 1;
        int actual = groupRepository.update(group);

        assertThat(actual, is(expected));
    }

    @Test
    void findAllTest() {
        int expected = 10;
        GroupUtils.insertRandomGroups(expected);
        List<Group> actual = groupRepository.findAll();

        assertThat(actual.size(), is(expected));
    }

    @Test
    void findByIdTest() {
        long id = GroupUtils.insertRandomGroup();
        Optional<Group> actual = groupRepository.findById(id);

        assertThat(actual.isPresent(), is(true));
    }

    @Test
    void saveGroupForQuizzesTest() {
        long id = GroupUtils.insertRandomGroup();
        GroupDto expected = GroupUtils.generateGroupDto();
        int actual = groupRepository.saveGroupForQuizzes(id, expected.getQuizzesIds());

        assertThat(actual, is(expected.getQuizzesIds().size()));
    }

    @Test
    void saveGroupForUsersTest() {
        long id = GroupUtils.insertRandomGroup();
        GroupDto expected = GroupUtils.generateGroupDto();
        int actual = groupRepository.saveGroupForUsers(id, expected.getUsersIds());

        assertThat(actual, is(expected.getUsersIds().size()));
    }
}