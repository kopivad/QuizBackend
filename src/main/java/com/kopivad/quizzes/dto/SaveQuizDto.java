package com.kopivad.quizzes.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Value;

import java.util.List;

@Value
public class SaveQuizDto {
    String title;
    String description;
    Integer total;
    Long authorId;
    List<SaveEvaluationStepDto> steps;
    List<SaveQuestionDto> questions;

    @JsonCreator
    public SaveQuizDto(
            @JsonProperty("title") String title,
            @JsonProperty("description") String description,
            @JsonProperty("total") Integer total,
            @JsonProperty("authorId") Long authorId,
            @JsonProperty("steps") List<SaveEvaluationStepDto> steps,
            @JsonProperty("questions") List<SaveQuestionDto> questions
    ) {
        this.title = title;
        this.description = description;
        this.total = total;
        this.authorId = authorId;
        this.steps = steps;
        this.questions = questions;
    }
}
