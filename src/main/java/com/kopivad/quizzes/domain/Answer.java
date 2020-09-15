package com.kopivad.quizzes.domain;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Value;

@Value
public class Answer {
    Long id;
    String body;
    boolean right;
    Long questionId;

    @JsonCreator
    public Answer(
            @JsonProperty("id") Long id,
            @JsonProperty("body") String body,
            @JsonProperty("right") boolean right,
            @JsonProperty("questionId") Long questionId
    ) {
        this.id = id;
        this.body = body;
        this.right = right;
        this.questionId = questionId;
    }
}
