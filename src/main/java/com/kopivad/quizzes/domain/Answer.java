package com.kopivad.quizzes.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import lombok.*;

@Value
@EqualsAndHashCode(of = {"id"})
@Builder(toBuilder = true)
@JsonDeserialize(builder = Answer.AnswerBuilder.class)
public class Answer {
    Long id;
    String body;
    boolean isRight;
    Question question;

    @JsonPOJOBuilder(withPrefix = "")
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class AnswerBuilder { }
}
