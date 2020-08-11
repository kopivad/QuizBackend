package com.kopivad.quizzes.mapper;

import com.kopivad.quizzes.domain.Quiz;
import com.kopivad.quizzes.domain.QuizAnswer;
import com.kopivad.quizzes.domain.QuizSession;
import com.kopivad.quizzes.domain.User;
import com.kopivad.quizzes.dto.QuizAnswerDto;
import com.kopivad.quizzes.dto.QuizSessionDto;
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
public class QuizSessionMapper {
    private final ModelMapper mapper;
    private final QuizAnswerMapper quizAnswerMapper;

    public QuizSession toEntity(QuizSessionDto quizSessionDto) {
        return mapper.map(quizSessionDto, QuizSession.QuizSessionBuilder.class).build();
    }

    public QuizSessionDto toDto(QuizSession quizSession) {
        return mapper.map(quizSession, QuizSessionDto.QuizSessionDtoBuilder.class).build();
    }

    @PostConstruct
    private void setupMapper() {
        mapper.createTypeMap(QuizSession.class, QuizSessionDto.QuizSessionDtoBuilder.class)
                .addMappings(m -> m.skip(QuizSessionDto.QuizSessionDtoBuilder::userId))
                .addMappings(m -> m.skip(QuizSessionDto.QuizSessionDtoBuilder::quizId))
                .addMappings(m -> m.skip(QuizSessionDto.QuizSessionDtoBuilder::results))
                .setPostConverter(toDtoConverter());

        mapper.createTypeMap(QuizSessionDto.class, QuizSession.QuizSessionBuilder.class)
                .addMappings(m -> m.skip(QuizSession.QuizSessionBuilder::user))
                .addMappings(m -> m.skip(QuizSession.QuizSessionBuilder::quiz))
                .addMappings(m -> m.skip(QuizSession.QuizSessionBuilder::results))
                .setPostConverter(toEntityConverter());
    }

    private Converter<QuizSessionDto, QuizSession.QuizSessionBuilder> toEntityConverter() {
        return context -> {
            QuizSessionDto source = context.getSource();
            QuizSession.QuizSessionBuilder destination = context.getDestination();
            mapSpecificFields(source, destination);
            return context.getDestination();
        };
    }

    private Converter<QuizSession, QuizSessionDto.QuizSessionDtoBuilder> toDtoConverter() {
        return context -> {
            QuizSession source = context.getSource();
            QuizSessionDto.QuizSessionDtoBuilder destination = context.getDestination();
            mapSpecificFields(source, destination);
            return context.getDestination();
        };
    }

    private void mapSpecificFields(QuizSession source, QuizSessionDto.QuizSessionDtoBuilder destination) {
        mapUserToDto(source, destination);
        mapQuizToDto(source, destination);
        mapResultsToDto(source, destination);
    }

    private void mapSpecificFields(QuizSessionDto source, QuizSession.QuizSessionBuilder destination) {
        mapUserToEntity(source, destination);
        mapQuizToEntity(source, destination);
        mapResultsToEntity(source, destination);
    }

    private void mapResultsToEntity(QuizSessionDto source, QuizSession.QuizSessionBuilder destination) {
        if (ObjectUtils.isNotEmpty(source.getResults())) {
            List<QuizAnswer> answers = source.getResults().stream().map(quizAnswerMapper::toEntity).collect(Collectors.toUnmodifiableList());
            destination.results(answers);
        } else {
            destination.results(Collections.emptyList());
        }
    }

    private void mapQuizToEntity(QuizSessionDto source, QuizSession.QuizSessionBuilder destination) {
        destination.quiz(Quiz.builder().id(source.getQuizId()).build());
    }

    private void mapUserToEntity(QuizSessionDto source, QuizSession.QuizSessionBuilder destination) {
        destination.user(User.builder().id(source.getUserId()).build()).build();
    }

    private void mapResultsToDto(QuizSession source, QuizSessionDto.QuizSessionDtoBuilder destination) {
        if (ObjectUtils.isNotEmpty(source.getResults())) {
            List<QuizAnswerDto> dtos = source.getResults().stream().map(quizAnswerMapper::toDto).collect(Collectors.toUnmodifiableList());
            destination.results(dtos);
        } else {
            destination.results(Collections.emptyList());
        }
    }

    private void mapQuizToDto(QuizSession source, QuizSessionDto.QuizSessionDtoBuilder destination) {
        destination.quizId(source.getQuiz().getId()).build();
    }

    private void mapUserToDto(QuizSession source, QuizSessionDto.QuizSessionDtoBuilder destination) {
        destination.userId(source.getUser().getId()).build();
    }
}
