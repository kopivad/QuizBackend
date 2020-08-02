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
@EqualsAndHashCode(of = "id")
@Builder(toBuilder = true)
@JsonDeserialize(builder = QuizSession.QuizSessionBuilder.class)
public class QuizSession {
    long id;
    Quiz quiz;
    User user;
    List<QuizAnswer> results;
    List<QuizHistory> histories;
    LocalDateTime date;

    @JsonPOJOBuilder(withPrefix = "")
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class QuizSessionBuilder { }
}
