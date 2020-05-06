package com.kopivad.quizzes.dto;

import lombok.Value;

import java.util.List;

@Value
public class FullQuizDto {
    String title;
    boolean active;
    String description;
    Long author_id;
    List<FullQuestionDto> questions;
}
