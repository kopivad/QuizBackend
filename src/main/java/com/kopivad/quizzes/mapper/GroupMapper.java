package com.kopivad.quizzes.mapper;

import com.kopivad.quizzes.domain.Group;
import com.kopivad.quizzes.domain.Quiz;
import com.kopivad.quizzes.domain.User;
import com.kopivad.quizzes.dto.GroupDto;
import com.kopivad.quizzes.dto.QuizDto;
import com.kopivad.quizzes.dto.UserDto;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.ObjectUtils;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class GroupMapper {
    private final ModelMapper mapper;
    private final UserMapper userMapper;
    private final QuizMapper quizMapper;

    public GroupDto toDto(Group group) {
        return mapper.map(group, GroupDto.GroupDtoBuilder.class).build();
    }

    public Group toEntity(GroupDto dto) {
        return mapper.map(dto, Group.GroupBuilder.class).build();
    }

    @PostConstruct
    private void setupMapper() {
        mapper.createTypeMap(Group.class, GroupDto.GroupDtoBuilder.class)
                .addMappings(m -> m.skip(GroupDto.GroupDtoBuilder::users))
                .addMappings(m -> m.skip(GroupDto.GroupDtoBuilder::quizzes))
                .setPostConverter(toDtoConverter());
        mapper.createTypeMap(GroupDto.class, Group.GroupBuilder.class)
                .addMappings(m -> m.skip(Group.GroupBuilder::users))
                .addMappings(m -> m.skip(Group.GroupBuilder::quizzes))
                .setPostConverter(toEntityConverter());
    }

    private Converter<GroupDto, Group.GroupBuilder> toEntityConverter() {
        return context -> {
            GroupDto source = context.getSource();
            Group.GroupBuilder destination = context.getDestination();
            mapSpecificFields(source, destination);
            return context.getDestination();
        };
    }

    private Converter<Group, GroupDto.GroupDtoBuilder> toDtoConverter() {
        return context -> {
            Group source = context.getSource();
            GroupDto.GroupDtoBuilder destination = context.getDestination();
            mapSpecificFields(source, destination);
            return context.getDestination();
        };
    }

    private void mapSpecificFields(Group source, GroupDto.GroupDtoBuilder destination) {
        mapUsersToDto(source, destination);
        mapQuizzesToDto(source, destination);
    }

    private void mapSpecificFields(GroupDto source, Group.GroupBuilder destination) {
        mapUsersToEntity(source, destination);
        mapQuizzesToEntity(source, destination);
    }

    private void mapQuizzesToDto(Group source, GroupDto.GroupDtoBuilder destination) {
        if (ObjectUtils.isNotEmpty(source.getQuizzes())) {
            List<QuizDto> dtos = source.getQuizzes()
                    .stream()
                    .map(quizMapper::toDto)
                    .collect(Collectors.toUnmodifiableList());
            destination.quizzes(dtos);
        } else {
            destination.quizzes(Collections.emptyList());
        }
    }

    private void mapUsersToDto(Group source, GroupDto.GroupDtoBuilder destination) {
        if (ObjectUtils.isNotEmpty(source.getUsers())) {
            List<UserDto> dtos = source.getUsers()
                    .stream()
                    .map(userMapper::toDto)
                    .collect(Collectors.toUnmodifiableList());
            destination.users(dtos);
        } else {
            destination.users(Collections.emptyList());
        }
    }

    private void mapQuizzesToEntity(GroupDto source, Group.GroupBuilder destination) {
        if (ObjectUtils.isNotEmpty(source.getQuizzes())) {
            List<Quiz> quizzes = source.getQuizzes()
                    .stream()
                    .map(quizMapper::toEntity)
                    .collect(Collectors.toUnmodifiableList());
            destination.quizzes(quizzes);
        } else {
            destination.quizzes(Collections.emptyList());
        }
    }

    private void mapUsersToEntity(GroupDto source, Group.GroupBuilder destination) {
        if (ObjectUtils.isNotEmpty(source.getUsers())) {
            List<User> users = source.getUsers()
                    .stream()
                    .map(userMapper::toEntity)
                    .collect(Collectors.toUnmodifiableList());
            destination.users(users);
        } else {
            destination.users(Collections.emptyList());
        }

    }

}
