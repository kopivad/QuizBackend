package com.kopivad.quizzes.domain;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Value;

@Value
public class Question {
    Long id;
    String title;
    int value;
    QuestionType type;
    Long quizId;

    @JsonCreator
    public Question(
            @JsonProperty("id") Long id,
            @JsonProperty("title") String title,
            @JsonProperty("value") int value,
            @JsonProperty("type") QuestionType type,
            @JsonProperty("quizId") Long quizId) {
        this.id = id;
        this.title = title;
        this.value = value;
        this.type = type;
        this.quizId = quizId;
    }
}
