package com.kopivad.quizzes.mapper;

import com.kopivad.quizzes.domain.*;
import com.kopivad.quizzes.dto.QuizDto;
import com.kopivad.quizzes.dto.QuizHistoryDto;
import com.kopivad.quizzes.dto.QuizSessionDto;
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
//                .addMappings(m -> m.skip(UserDto.UserDtoBuilder::groupId))
                .addMappings(m -> m.skip(UserDto.UserDtoBuilder::histories))
                .addMappings(m -> m.skip(UserDto.UserDtoBuilder::quizzes))
                .addMappings(m -> m.skip(UserDto.UserDtoBuilder::sessions))
                .setPostConverter(toDtoConverter());

        mapper.createTypeMap(UserDto.class, User.UserBuilder.class)
//                .addMappings(m -> m.skip(User.UserBuilder::group))
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
//        mapGroupToDto(source, destination);
        mapHistoriesToDto(source, destination);
        mapQuizzesToDto(source, destination);
        mapSessionsToDto(source, destination);
    }

    private void mapSpecificFields(UserDto source, User.UserBuilder destination) {
//        mapGroupToEntity(source, destination);
        mapHistoriesToEntity(source, destination);
        mapQuizzesToEntity(source, destination);
        mapSessionsToEntity(source, destination);
    }

    private void mapSessionsToEntity(UserDto source, User.UserBuilder destination) {
        if (ObjectUtils.isNotEmpty(source.getSessions())) {
            List<QuizSession> sessions = source
                    .getSessions()
                    .stream()
                    .map(quizSessionMapper::toEntity)
                    .collect(Collectors.toUnmodifiableList());

            destination.sessions(sessions);
        } else {
            destination.sessions(Collections.emptyList());
        }
    }

    private void mapQuizzesToEntity(UserDto source, User.UserBuilder destination) {
        if (ObjectUtils.isNotEmpty(source.getQuizzes())) {
            List<Quiz> quizzes = source
                    .getQuizzes()
                    .stream()
                    .map(quizMapper::toEntity)
                    .collect(Collectors.toUnmodifiableList());

            destination.quizzes(quizzes);
        } else {
            destination.quizzes(Collections.emptyList());
        }
    }

    private void mapHistoriesToEntity(UserDto source, User.UserBuilder destination) {
        if (ObjectUtils.isNotEmpty(source.getHistories())) {
            List<QuizHistory> histories = source
                    .getHistories()
                    .stream()
                    .map(quizHistoryMapper::toEntity)
                    .collect(Collectors.toUnmodifiableList());

            destination.histories(histories);
        } else {
            destination.histories(Collections.emptyList());
        }
    }

//    private void mapGroupToEntity(UserDto source, User.UserBuilder destination) {
//        destination.group(Group.builder().id(source.getGroupId()).build()).build();
//    }

    private void mapSessionsToDto(User source, UserDto.UserDtoBuilder destination) {
        if (ObjectUtils.isNotEmpty(source.getSessions())) {
            List<QuizSessionDto> dtos = source
                    .getSessions()
                    .stream()
                    .map(quizSessionMapper::toDto)
                    .collect(Collectors.toUnmodifiableList());

            destination.sessions(dtos);
        } else {
            destination.sessions(Collections.emptyList());
        }
    }

    private void mapQuizzesToDto(User source, UserDto.UserDtoBuilder destination) {
        if (ObjectUtils.isNotEmpty(source.getQuizzes())) {
            List<QuizDto> dtos = source
                    .getQuizzes()
                    .stream()
                    .map(quizMapper::toDto)
                    .collect(Collectors.toUnmodifiableList());

            destination.quizzes(dtos);
        } else {
            destination.quizzes(Collections.emptyList());
        }
    }

    private void mapHistoriesToDto(User source, UserDto.UserDtoBuilder destination) {
        if (ObjectUtils.isNotEmpty(source.getHistories())) {
            List<QuizHistoryDto> dtos = source
                    .getHistories()
                    .stream()
                    .map(quizHistoryMapper::toDto)
                    .collect(Collectors.toUnmodifiableList());

            destination.histories(dtos);
        } else {
            destination.histories(Collections.emptyList());
        }
    }

//    private void mapGroupToDto(User source, UserDto.UserDtoBuilder destination) {
//        destination.groupId(source.getGroup().getId()).build();
//    }

    public List<UserDto> allToDto(List<User> users) {
        return users
                .stream()
                .map(this::toDto)
                .collect(Collectors.toUnmodifiableList());
    }
}
