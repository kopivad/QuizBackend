package com.kopivad.quizzes.dto;

import lombok.Value;

@Value
public class QuizHistoryDto {
    int total;
    String rating;
    Long sessionId;
    Long userId;
    String pdfFilename;
    String csvFilename;
}
