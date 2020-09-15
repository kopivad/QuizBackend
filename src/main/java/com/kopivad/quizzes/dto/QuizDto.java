package com.kopivad.quizzes.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Value;

import java.util.List;

@Value
public class QuizDto {
    String title;
    String description;
    Integer total;
    Long authorId;
    List<EvaluationStepDto> steps;
    List<QuestionDto> questions;

    @JsonCreator
    public QuizDto(
            @JsonProperty("title") String title,
            @JsonProperty("description") String description,
            @JsonProperty("total") Integer total,
            @JsonProperty("authorId") Long authorId,
            @JsonProperty("steps") List<EvaluationStepDto> steps,
            @JsonProperty("questions") List<QuestionDto> questions
    ) {
        this.title = title;
        this.description = description;
        this.total = total;
        this.authorId = authorId;
        this.steps = steps;
        this.questions = questions;
    }
}
