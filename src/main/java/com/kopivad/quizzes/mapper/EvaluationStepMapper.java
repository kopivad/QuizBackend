package com.kopivad.quizzes.mapper;

import com.kopivad.quizzes.domain.EvaluationStep;
import com.kopivad.quizzes.domain.Quiz;
import com.kopivad.quizzes.dto.EvaluationStepDto;
import lombok.RequiredArgsConstructor;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
@RequiredArgsConstructor
public class EvaluationStepMapper {
    private final ModelMapper mapper;

    public EvaluationStepDto toDto(EvaluationStep step) {
        return mapper.map(step, EvaluationStepDto.EvaluationStepDtoBuilder.class).build();
    }

    public EvaluationStep toEntity(EvaluationStepDto dto) {
        return mapper.map(dto, EvaluationStep.EvaluationStepBuilder.class).build();
    }

    @PostConstruct
    private void setupMapper() {
        mapper.createTypeMap(EvaluationStep.class, EvaluationStepDto.EvaluationStepDtoBuilder.class)
                .addMappings(m -> m.skip(EvaluationStepDto.EvaluationStepDtoBuilder::quizId))
                .setPostConverter(toDtoConverter());
        mapper.createTypeMap(EvaluationStepDto.class, EvaluationStep.EvaluationStepBuilder.class)
                .addMappings(m -> m.skip(EvaluationStep.EvaluationStepBuilder::quiz))
                .setPostConverter(toEntityConverter());
    }

    private Converter<EvaluationStepDto, EvaluationStep.EvaluationStepBuilder> toEntityConverter() {
        return context -> {
            EvaluationStepDto source = context.getSource();
            EvaluationStep.EvaluationStepBuilder destination = context.getDestination();
            mapSpecificFields(source, destination);
            return context.getDestination();
        };
    }

    private Converter<EvaluationStep, EvaluationStepDto.EvaluationStepDtoBuilder> toDtoConverter() {
        return context -> {
            EvaluationStep source = context.getSource();
            EvaluationStepDto.EvaluationStepDtoBuilder destination = context.getDestination();
            mapSpecificFields(source, destination);
            return context.getDestination();
        };
    }

    private void mapSpecificFields(EvaluationStep source, EvaluationStepDto.EvaluationStepDtoBuilder destination) {
        destination.quizId(source.getQuiz().getId()).build();
    }

    private void mapSpecificFields(EvaluationStepDto source, EvaluationStep.EvaluationStepBuilder destination) {
        destination.quiz(Quiz.builder().id(source.getQuizId()).build());
    }
}
