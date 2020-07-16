package com.kopivad.quizzes.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Value;

import java.time.LocalDateTime;
import java.util.List;

@Value
@Builder(toBuilder = true)
@EqualsAndHashCode(of = "id")
@JsonDeserialize(builder = QuizDto.QuizDtoBuilder.class)
public class QuizDto {
    long id;
    String title;
    String description;
    int total;
    boolean active;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    LocalDateTime creationDate;
    long authorId;
    List<QuestionDto> questions;
    List<EvaluationStepDto> evaluationSteps;

    @JsonPOJOBuilder(withPrefix = "")
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class QuizDtoBuilder { }
}
