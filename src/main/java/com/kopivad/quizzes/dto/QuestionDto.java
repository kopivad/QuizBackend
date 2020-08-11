package com.kopivad.quizzes.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import com.kopivad.quizzes.domain.QuestionType;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Value;

import java.util.List;

@Value
@EqualsAndHashCode(of = {"id"})
@Builder(toBuilder = true)
@JsonDeserialize(builder = QuestionDto.QuestionDtoBuilder.class)
public class QuestionDto {
    long id;
    String title;
    int value;
    QuestionType type;
    List<AnswerDto> answers;
    List<QuizAnswerDto> quizAnswers;
    long quizId;

    @JsonPOJOBuilder(withPrefix = "")
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class QuestionDtoBuilder { }
}
