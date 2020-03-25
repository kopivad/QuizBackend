package com.kopivad.testingsystem.dto;

import lombok.Data;

@Data
public class AnswerDto {
    private String text;

    private boolean isRight;

    private Long questionId;
}
