package com.kopivad.quizzes.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Value;

@Value
public class SaveQuizAnswerDto {
    long questionId;
    long sessionId;
    long answerId;

    @JsonCreator
    public SaveQuizAnswerDto(
            @JsonProperty("questionId") long questionId,
            @JsonProperty("sessionId") long sessionId,
            @JsonProperty("answerId") long answerId) {
        this.questionId = questionId;
        this.sessionId = sessionId;
        this.answerId = answerId;
    }
}
