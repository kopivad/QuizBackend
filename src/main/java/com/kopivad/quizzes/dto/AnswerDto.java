package com.kopivad.quizzes.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Value;

import java.util.List;

@Value
@EqualsAndHashCode(of = {"id"})
@Builder(toBuilder = true)
@JsonDeserialize(builder = AnswerDto.AnswerDtoBuilder.class)
public class AnswerDto {
    Long id;
    String body;
    boolean isRight;
    long questionId;
    List<QuizAnswerDto> quizAnswers;

    @JsonPOJOBuilder(withPrefix = "")
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class AnswerDtoBuilder { }
}
