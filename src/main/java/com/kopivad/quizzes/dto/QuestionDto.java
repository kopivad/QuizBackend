package com.kopivad.quizzes.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.kopivad.quizzes.domain.QuestionType;
import lombok.Value;

import java.util.List;

@Value
public class QuestionDto {
    String title;
    Integer value;
    QuestionType type;
    Long quizId;
    List<AnswerDto> answers;

    @JsonCreator
    public QuestionDto(
            @JsonProperty("title") String title,
            @JsonProperty("value") Integer value,
            @JsonProperty("type") QuestionType type,
            @JsonProperty("quizId") Long quizId,
            @JsonProperty("answers") List<AnswerDto> answers
    ) {
        this.title = title;
        this.value = value;
        this.type = type;
        this.quizId = quizId;
        this.answers = answers;
    }
}
