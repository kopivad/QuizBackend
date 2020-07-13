package com.kopivad.quizzes.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import lombok.*;

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
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    LocalDateTime creationDate;
    User author;
    List<Question> questions;
    List<EvaluationStep> evaluationSteps;

    @JsonPOJOBuilder(withPrefix = "")
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class QuizBuilder { }
}
