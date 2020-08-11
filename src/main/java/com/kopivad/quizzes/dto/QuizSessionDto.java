package com.kopivad.quizzes.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import lombok.Builder;
import lombok.Value;

import java.time.LocalDateTime;
import java.util.List;

@Value
@Builder(toBuilder = true)
@JsonDeserialize(builder = QuizSessionDto.QuizSessionDtoBuilder.class)
public class QuizSessionDto {
    long id;
    long quizId;
    long userId;
    List<QuizAnswerDto> results;
    List<QuizHistoryDto> histories;
    LocalDateTime date;

    @JsonPOJOBuilder(withPrefix = "")
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class QuizSessionDtoBuilder { }
}
