package com.kopivad.quizzes.mapper;

import com.kopivad.quizzes.domain.*;
import com.kopivad.quizzes.dto.EvaluationStepDto;
import com.kopivad.quizzes.dto.QuestionDto;
import com.kopivad.quizzes.dto.QuizDto;
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
public class QuizMapper {
    private final ModelMapper mapper;
    private final QuestionMapper questionMapper;
    private final EvaluationStepMapper stepMapper;

    public Quiz toEntity(QuizDto dto) {
        return mapper.map(dto, Quiz.QuizBuilder.class).build();
    }

    public QuizDto toDto(Quiz quiz) {
        return mapper.map(quiz, QuizDto.QuizDtoBuilder.class).build();
    }

    @PostConstruct
    private void setupMapper() {
        mapper.createTypeMap(Quiz.class, QuizDto.QuizDtoBuilder.class)
                .addMappings(m -> m.skip(QuizDto.QuizDtoBuilder::authorId))
                .addMappings(m -> m.skip(QuizDto.QuizDtoBuilder::groupId))
                .addMappings(m -> m.skip(QuizDto.QuizDtoBuilder::questions))
                .addMappings(m -> m.skip(QuizDto.QuizDtoBuilder::evaluationSteps))
                .setPostConverter(toDtoConverter());

        mapper.createTypeMap(QuizDto.class, Quiz.QuizBuilder.class)
                .addMappings(m -> m.skip(Quiz.QuizBuilder::author))
                .addMappings(m -> m.skip(Quiz.QuizBuilder::group))
                .addMappings(m -> m.skip(Quiz.QuizBuilder::questions))
                .addMappings(m -> m.skip(Quiz.QuizBuilder::evaluationSteps))
                .setPostConverter(toEntityConverter());
    }

    private Converter<QuizDto, Quiz.QuizBuilder> toEntityConverter() {
        return context -> {
            QuizDto source = context.getSource();
            Quiz.QuizBuilder destination = context.getDestination();
            mapSpecificFields(source, destination);
            return context.getDestination();
        };
    }

    private Converter<Quiz, QuizDto.QuizDtoBuilder> toDtoConverter() {
        return context -> {
            Quiz source = context.getSource();
            QuizDto.QuizDtoBuilder destination = context.getDestination();
            mapSpecificFields(source, destination);
            return context.getDestination();
        };
    }

    private void mapSpecificFields(Quiz source, QuizDto.QuizDtoBuilder destination) {
        mapUserToDto(source, destination);
        mapGroupToDto(source, destination);
        mapQuestionsToDto(source, destination);
        mapStepsToDto(source, destination);
    }

    private void mapSpecificFields(QuizDto source, Quiz.QuizBuilder destination) {
        mapUserToEntity(source, destination);
        mapGroupToEntity(source, destination);
        mapQuestionsToEntity(source, destination);
        mapStepsToEntity(source, destination);
    }

    private void mapStepsToEntity(QuizDto source, Quiz.QuizBuilder destination) {
        if (ObjectUtils.isNotEmpty(source.getQuestions())) {
            List<EvaluationStep> steps = source.getEvaluationSteps().stream().map(stepMapper::toEntity).collect(Collectors.toUnmodifiableList());
            destination.evaluationSteps(steps);
        } else {
            destination.evaluationSteps(Collections.emptyList());
        }
    }

    private void mapQuestionsToEntity(QuizDto source, Quiz.QuizBuilder destination) {
        if (ObjectUtils.isNotEmpty(source.getQuestions())) {
            List<Question> questions = source.getQuestions().stream().map(questionMapper::toEntity).collect(Collectors.toUnmodifiableList());
            destination.questions(questions);
        } else {
            destination.questions(Collections.emptyList());
        }
    }

    private void mapGroupToEntity(QuizDto source, Quiz.QuizBuilder destination) {
        destination.group(Group.builder().id(source.getGroupId()).build()).build();
    }

    private void mapUserToEntity(QuizDto source, Quiz.QuizBuilder destination) {
        destination.author(User.builder().id(source.getAuthorId()).build()).build();
    }

    private void mapStepsToDto(Quiz source, QuizDto.QuizDtoBuilder destination) {
        if (ObjectUtils.isNotEmpty(source.getQuestions())) {
            List<EvaluationStepDto> steps = source.getEvaluationSteps().stream().map(stepMapper::toDto).collect(Collectors.toUnmodifiableList());
            destination.evaluationSteps(steps);
        } else {
            destination.evaluationSteps(Collections.emptyList());
        }
    }

    private void mapQuestionsToDto(Quiz source, QuizDto.QuizDtoBuilder destination) {
        if (ObjectUtils.isNotEmpty(source.getQuestions())) {
            List<QuestionDto> questions = source.getQuestions().stream().map(questionMapper::toDto).collect(Collectors.toUnmodifiableList());
            destination.questions(questions);
        } else {
            destination.questions(Collections.emptyList());
        }
    }

    private void mapGroupToDto(Quiz source, QuizDto.QuizDtoBuilder destination) {
        destination.groupId(source.getGroup().getId()).build();
    }

    private void mapUserToDto(Quiz source, QuizDto.QuizDtoBuilder destination) {
        destination.authorId(source.getAuthor().getId()).build();
    }

    public List<QuizDto> allToDto(List<Quiz> quizzes) {
        return quizzes
                .stream()
                .map(this::toDto)
                .collect(Collectors.toUnmodifiableList());
    }
}
