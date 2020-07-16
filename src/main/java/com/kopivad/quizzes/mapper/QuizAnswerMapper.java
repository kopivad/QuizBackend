package com.kopivad.quizzes.mapper;

import com.kopivad.quizzes.domain.Answer;
import com.kopivad.quizzes.domain.Question;
import com.kopivad.quizzes.domain.QuizAnswer;
import com.kopivad.quizzes.domain.QuizSession;
import com.kopivad.quizzes.dto.QuizAnswerDto;
import lombok.RequiredArgsConstructor;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
@RequiredArgsConstructor
public class QuizAnswerMapper {
    private final ModelMapper mapper;

    public QuizAnswerDto toDto(QuizAnswer quizAnswer) {
        return mapper.map(quizAnswer, QuizAnswerDto.QuizAnswerDtoBuilder.class).build();
    }

    public QuizAnswer toEntity(QuizAnswerDto quizAnswerDto) {
        return mapper.map(quizAnswerDto, QuizAnswer.QuizAnswerBuilder.class).build();
    }

    @PostConstruct
    private void setupMapper() {
        mapper.createTypeMap(QuizAnswer.class, QuizAnswerDto.QuizAnswerDtoBuilder.class)
                .addMappings(m -> m.skip(QuizAnswerDto.QuizAnswerDtoBuilder::questionId))
                .addMappings(m -> m.skip(QuizAnswerDto.QuizAnswerDtoBuilder::sessionId))
                .addMappings(m -> m.skip(QuizAnswerDto.QuizAnswerDtoBuilder::answerId))
                .setPostConverter(toDtoConverter());

        mapper.createTypeMap(QuizAnswerDto.class, QuizAnswer.QuizAnswerBuilder.class)
                .addMappings(m -> m.skip(QuizAnswer.QuizAnswerBuilder::question))
                .addMappings(m -> m.skip(QuizAnswer.QuizAnswerBuilder::session))
                .addMappings(m -> m.skip(QuizAnswer.QuizAnswerBuilder::answer))
                .setPostConverter(toEntityConverter());
    }

    private Converter<QuizAnswerDto, QuizAnswer.QuizAnswerBuilder> toEntityConverter() {
        return context -> {
            QuizAnswerDto source = context.getSource();
            QuizAnswer.QuizAnswerBuilder destination = context.getDestination();
            mapSpecificFields(source, destination);
            return context.getDestination();
        };
    }

    private Converter<QuizAnswer, QuizAnswerDto.QuizAnswerDtoBuilder> toDtoConverter() {
        return context -> {
            QuizAnswer source = context.getSource();
            QuizAnswerDto.QuizAnswerDtoBuilder destination = context.getDestination();
            mapSpecificFields(source, destination);
            return context.getDestination();
        };
    }

    private void mapSpecificFields(QuizAnswer source, QuizAnswerDto.QuizAnswerDtoBuilder destination) {
        destination.questionId(source.getQuestion().getId()).build();
        destination.sessionId(source.getSession().getId()).build();
        destination.answerId(source.getAnswer().getId()).build();
    }

    private void mapSpecificFields(QuizAnswerDto source, QuizAnswer.QuizAnswerBuilder destination) {
        destination.question(Question.builder().id(source.getQuestionId()).build());
        destination.session(QuizSession.builder().id(source.getSessionId()).build());
        destination.answer(Answer.builder().id(source.getAnswerId()).build());
    }
}
