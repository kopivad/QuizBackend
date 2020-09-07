package com.kopivad.quizzes.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import com.kopivad.quizzes.domain.QuizSession;
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
    LocalDateTime creationDate;
    long authorId;
    List<QuizSession> sessions;
    List<QuestionDto> questions;
    List<EvaluationStepDto> evaluationSteps;
    List<GroupDto> groups;

    @JsonPOJOBuilder(withPrefix = "")
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class QuizDtoBuilder { }
}
