package com.kopivad.quizzes.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Value;

import java.time.LocalDateTime;
import java.util.List;

@Value
@EqualsAndHashCode(of = {"id"})
@Builder(toBuilder = true)
@JsonDeserialize(builder = Quiz.QuizBuilder.class)
public class Quiz {
    Long id;
    String title;
    String description;
    int total;
    boolean active;
    LocalDateTime creationDate;
    User author;
    List<QuizSession> sessions;
    List<Question> questions;
    List<EvaluationStep> evaluationSteps;
    Group group;

    @JsonPOJOBuilder(withPrefix = "")
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class QuizBuilder { }
}
