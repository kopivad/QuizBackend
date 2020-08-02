package com.kopivad.quizzes.mapper;

import com.kopivad.quizzes.domain.Group;
import com.kopivad.quizzes.domain.Quiz;
import com.kopivad.quizzes.domain.User;
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
        destination.authorId(source.getAuthor().getId()).build();
        destination.groupId(source.getGroup().getId()).build();
        destination.questions(
                ObjectUtils.isNotEmpty(source.getQuestions())
                        ? source.getQuestions().stream().map(questionMapper::toDto).collect(Collectors.toUnmodifiableList())
                        : Collections.emptyList()
        );
        destination.evaluationSteps(
                ObjectUtils.isNotEmpty(source.getQuestions())
                        ? source.getEvaluationSteps().stream().map(stepMapper::toDto).collect(Collectors.toUnmodifiableList())
                        : Collections.emptyList()
        );
    }

    private void mapSpecificFields(QuizDto source, Quiz.QuizBuilder destination) {
        destination.author(User.builder().id(source.getAuthorId()).build()).build();
        destination.group(Group.builder().id(source.getGroupId()).build()).build();
        destination.questions(
                ObjectUtils.isNotEmpty(source.getQuestions())
                        ? source.getQuestions().stream().map(questionMapper::toEntity).collect(Collectors.toUnmodifiableList())
                        : Collections.emptyList()
        );
        destination.evaluationSteps(
                ObjectUtils.isNotEmpty(source.getQuestions())
                        ? source.getEvaluationSteps().stream().map(stepMapper::toEntity).collect(Collectors.toUnmodifiableList())
                        : Collections.emptyList()
        );
    }

    public List<QuizDto> allToDto(List<Quiz> quizzes) {
        return quizzes
                .stream()
                .map(this::toDto)
                .collect(Collectors.toUnmodifiableList());
    }
}
