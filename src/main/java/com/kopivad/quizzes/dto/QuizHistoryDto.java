package com.kopivad.quizzes.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Value;

@Value
@Builder(toBuilder = true)
@EqualsAndHashCode(of = "id")
@JsonDeserialize(builder = QuizHistoryDto.QuizHistoryDtoBuilder.class)
public class QuizHistoryDto {
    long id;
    int total;
    String rating;
    long sessionId;
    long userId;
    String pdfFilename;
    String csvFilename;

    @JsonPOJOBuilder(withPrefix = "")
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class QuizHistoryDtoBuilder { }
}
