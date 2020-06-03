package com.kopivad.quizzes.form;

import lombok.Builder;
import lombok.Value;

@Value
@Builder(toBuilder = true)
public class AnswerForm {
    String body;
    boolean right;
    Long questionId;
}
