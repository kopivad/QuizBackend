package com.kopivad.quizzes.mapper;

import com.kopivad.quizzes.domain.Answer;
import com.kopivad.quizzes.domain.Question;
import com.kopivad.quizzes.dto.AnswerDto;
import lombok.RequiredArgsConstructor;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
@RequiredArgsConstructor
public class AnswerMapper {
    private final ModelMapper mapper;

    public AnswerDto toDto(Answer answer) {
        return mapper.map(answer, AnswerDto.AnswerDtoBuilder.class).build();
    }

    public Answer toEntity(AnswerDto dto) {
        return mapper.map(dto, Answer.AnswerBuilder.class).build();
    }

    @PostConstruct
    private void setupMapper() {
        mapper.createTypeMap(Answer.class, AnswerDto.AnswerDtoBuilder.class)
                .addMappings(m -> m.skip(AnswerDto.AnswerDtoBuilder::questionId))
                .setPostConverter(toDtoConverter());
        mapper.createTypeMap(AnswerDto.class, Answer.AnswerBuilder.class)
                .addMappings(m -> m.skip(Answer.AnswerBuilder::question))
                .setPostConverter(toEntityConverter());
    }

    private Converter<AnswerDto, Answer.AnswerBuilder> toEntityConverter() {
        return context -> {
            AnswerDto source = context.getSource();
            Answer.AnswerBuilder destination = context.getDestination();
            mapSpecificFields(source, destination);
            return context.getDestination();
        };
    }

    private Converter<Answer, AnswerDto.AnswerDtoBuilder> toDtoConverter() {
        return context -> {
            Answer source = context.getSource();
            AnswerDto.AnswerDtoBuilder destination = context.getDestination();
            mapSpecificFields(source, destination);
            return context.getDestination();
        };
    }

    private void mapSpecificFields(Answer source, AnswerDto.AnswerDtoBuilder destination) {
        mapQuestionToDto(source, destination);
    }

    private void mapSpecificFields(AnswerDto source, Answer.AnswerBuilder destination) {
        mapAnswerToEntity(source, destination);
    }

    private void mapQuestionToDto(Answer source, AnswerDto.AnswerDtoBuilder destination) {
        destination.questionId(source.getQuestion().getId()).build();
    }

    private void mapAnswerToEntity(AnswerDto source, Answer.AnswerBuilder destination) {
        destination.question(Question.builder().id(source.getQuestionId()).build());
    }
}
