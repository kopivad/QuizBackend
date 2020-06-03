package com.kopivad.quizzes.form;

import lombok.Builder;
import lombok.Value;

import java.util.List;

@Value
@Builder(toBuilder = true)
public class QuizForm {
    String title;
    String description;
    List<QuestionForm> questions;
    Long authorId;
}