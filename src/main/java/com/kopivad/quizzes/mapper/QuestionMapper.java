package com.kopivad.quizzes.mapper;

import com.kopivad.quizzes.domain.Answer;
import com.kopivad.quizzes.domain.Question;
import com.kopivad.quizzes.domain.Quiz;
import com.kopivad.quizzes.dto.AnswerDto;
import com.kopivad.quizzes.dto.QuestionDto;
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
public class QuestionMapper {
    private final ModelMapper mapper;
    private final AnswerMapper answerMapper;

    public QuestionDto toDto(Question question) {
        return mapper.map(question, QuestionDto.QuestionDtoBuilder.class).build();
    }

    public Question toEntity(QuestionDto questionDto) {
        return mapper.map(questionDto, Question.QuestionBuilder.class).build();
    }

    @PostConstruct
    private void setupMapper() {
        mapper.createTypeMap(Question.class, QuestionDto.QuestionDtoBuilder.class)
                .addMappings(m -> m.skip(QuestionDto.QuestionDtoBuilder::quizId))
                .addMappings(m -> m.skip(QuestionDto.QuestionDtoBuilder::answers))
                .setPostConverter(toDtoConverter());
        mapper.createTypeMap(QuestionDto.class, Question.QuestionBuilder.class)
                .addMappings(m -> m.skip(Question.QuestionBuilder::quiz))
                .addMappings(m -> m.skip(Question.QuestionBuilder::answers))
                .setPostConverter(toEntityConverter());
    }

    private Converter<QuestionDto, Question.QuestionBuilder> toEntityConverter() {
        return context -> {
            QuestionDto source = context.getSource();
            Question.QuestionBuilder destination = context.getDestination();
            mapSpecificFields(source, destination);
            return context.getDestination();
        };
    }

    private Converter<Question, QuestionDto.QuestionDtoBuilder> toDtoConverter() {
        return context -> {
            Question source = context.getSource();
            QuestionDto.QuestionDtoBuilder destination = context.getDestination();
            mapSpecificFields(source, destination);
            return context.getDestination();
        };
    }

    private void mapSpecificFields(Question source, QuestionDto.QuestionDtoBuilder destination) {
        mapQuizToDto(source, destination);
        mapAnswersToDto(source, destination);
    }

    private void mapSpecificFields(QuestionDto source, Question.QuestionBuilder destination) {
        mapQuizToEntity(source, destination);
        mapAnswersToEntity(source, destination);
    }

    private void mapAnswersToEntity(QuestionDto source, Question.QuestionBuilder destination) {
        if (ObjectUtils.isNotEmpty(source.getAnswers())) {
            List<Answer> answers = source.getAnswers()
                    .stream()
                    .map(answerMapper::toEntity)
                    .collect(Collectors.toUnmodifiableList());
            destination.answers(answers);
        } else {
            destination.answers(Collections.emptyList());
        }
    }

    private void mapAnswersToDto(Question source, QuestionDto.QuestionDtoBuilder destination) {
        if (ObjectUtils.isNotEmpty(source.getAnswers())) {
            List<AnswerDto> answers = source.getAnswers().stream().map(answerMapper::toDto).collect(Collectors.toUnmodifiableList());
            destination.answers(answers);
        } else {
            destination.answers(Collections.emptyList());
        }
    }

    private void mapQuizToEntity(QuestionDto source, Question.QuestionBuilder destination) {
        destination.quiz(Quiz.builder().id(source.getQuizId()).build()).build();
    }

    private void mapQuizToDto(Question source, QuestionDto.QuestionDtoBuilder destination) {
        destination.quizId(source.getQuiz().getId()).build();
    }
}
