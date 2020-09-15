package com.kopivad.quizzes.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.kopivad.quizzes.domain.QuizAnswer;
import com.kopivad.quizzes.domain.QuizHistory;
import lombok.Value;

import java.util.List;

@Value
public class SaveQuizSessionDto {
    long quizId;
    long userId;
    List<QuizAnswer> results;
    List<QuizHistory> histories;

    @JsonCreator
    public SaveQuizSessionDto(
            @JsonProperty("quizId") long quizId,
            @JsonProperty("userId") long userId,
            @JsonProperty("results") List<QuizAnswer> results,
            @JsonProperty("histories") List<QuizHistory> histories) {
        this.quizId = quizId;
        this.userId = userId;
        this.results = results;
        this.histories = histories;
    }
}
