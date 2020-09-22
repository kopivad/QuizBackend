package com.kopivad.quizzes.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Value;

@Value
public class EvaluationStepDto {
    Integer minTotal;
    Integer maxTotal;
    String rating;
    Long quizId;

    @JsonCreator
    public EvaluationStepDto(
            @JsonProperty("minTotal") Integer minTotal,
            @JsonProperty("maxTotal") Integer maxTotal,
            @JsonProperty("rating") String rating,
            @JsonProperty("quizId") Long quizId
    ) {
        this.minTotal = minTotal;
        this.maxTotal = maxTotal;
        this.rating = rating;
        this.quizId = quizId;
    }
}
