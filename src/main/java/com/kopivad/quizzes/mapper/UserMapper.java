package com.kopivad.quizzes.mapper;

import com.kopivad.quizzes.domain.Group;
import com.kopivad.quizzes.domain.User;
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
public class UserMapper {
    private final ModelMapper mapper;
    private final QuizHistoryMapper quizHistoryMapper;
    private final QuizMapper quizMapper;
    private final QuizSessionMapper quizSessionMapper;

    public User toEntity(UserDto dto) {
        return mapper.map(dto, User.UserBuilder.class).build();
    }

    public UserDto toDto(User user) {
        return mapper.map(user, UserDto.UserDtoBuilder.class).build();
    }

    @PostConstruct
    private void setupMapper() {
        mapper.createTypeMap(User.class, UserDto.UserDtoBuilder.class)
                .addMappings(m -> m.skip(UserDto.UserDtoBuilder::groupId))
                .addMappings(m -> m.skip(UserDto.UserDtoBuilder::histories))
                .addMappings(m -> m.skip(UserDto.UserDtoBuilder::quizzes))
                .addMappings(m -> m.skip(UserDto.UserDtoBuilder::sessions))
                .setPostConverter(toDtoConverter());

        mapper.createTypeMap(UserDto.class, User.UserBuilder.class)
                .addMappings(m -> m.skip(User.UserBuilder::group))
                .addMappings(m -> m.skip(User.UserBuilder::histories))
                .addMappings(m -> m.skip(User.UserBuilder::sessions))
                .setPostConverter(toEntityConverter());
    }

    private Converter<UserDto, User.UserBuilder> toEntityConverter() {
        return context -> {
            UserDto source = context.getSource();
            User.UserBuilder destination = context.getDestination();
            mapSpecificFields(source, destination);
            return context.getDestination();
        };
    }

    private Converter<User, UserDto.UserDtoBuilder> toDtoConverter() {
        return context -> {
            User source = context.getSource();
            UserDto.UserDtoBuilder destination = context.getDestination();
            mapSpecificFields(source, destination);
            return context.getDestination();
        };
    }

    private void mapSpecificFields(User source, UserDto.UserDtoBuilder destination) {
        destination.groupId(source.getGroup().getId()).build();

        destination.histories(
                ObjectUtils.isNotEmpty(source.getHistories())
                        ? source.getHistories().stream().map(quizHistoryMapper::toDto).collect(Collectors.toUnmodifiableList())
                        : Collections.emptyList()
        );
        destination.quizzes(
                ObjectUtils.isNotEmpty(source.getQuizzes())
                        ? source.getQuizzes().stream().map(quizMapper::toDto).collect(Collectors.toUnmodifiableList())
                        : Collections.emptyList()
        );
        destination.sessions(
                ObjectUtils.isNotEmpty(source.getSessions())
                        ? source.getSessions().stream().map(quizSessionMapper::toDto).collect(Collectors.toUnmodifiableList())
                        : Collections.emptyList()
        );
    }

    private void mapSpecificFields(UserDto source, User.UserBuilder destination) {
        destination.group(Group.builder().id(source.getGroupId()).build()).build();

        destination.histories(
                ObjectUtils.isNotEmpty(source.getHistories())
                        ? source.getHistories().stream().map(quizHistoryMapper::toEntity).collect(Collectors.toUnmodifiableList())
                        : Collections.emptyList()
        );
        destination.quizzes(
                ObjectUtils.isNotEmpty(source.getQuizzes())
                        ? source.getQuizzes().stream().map(quizMapper::toEntity).collect(Collectors.toUnmodifiableList())
                        : Collections.emptyList()
        );
        destination.sessions(
                ObjectUtils.isNotEmpty(source.getSessions())
                        ? source.getSessions().stream().map(quizSessionMapper::toEntity).collect(Collectors.toUnmodifiableList())
                        : Collections.emptyList()
        );
    }

    public List<UserDto> allToDto(List<User> users) {
        return users
                .stream()
                .map(this::toDto)
                .collect(Collectors.toUnmodifiableList());
    }
}
