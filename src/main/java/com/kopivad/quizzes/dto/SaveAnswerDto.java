package com.kopivad.quizzes.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Value;

@Value
public class SaveAnswerDto {
    String body;
    boolean right;
    Long questionId;

    @JsonCreator
    public SaveAnswerDto(
            @JsonProperty("body") String body,
            @JsonProperty("right") boolean right,
            @JsonProperty("questionId") Long questionId
    ) {
        this.body = body;
        this.right = right;
        this.questionId = questionId;
    }
}
