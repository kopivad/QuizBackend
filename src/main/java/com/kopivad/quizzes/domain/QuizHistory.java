package com.kopivad.quizzes.domain;

import lombok.Value;

@Value
public class QuizHistory {
    long id;
    int total;
    String rating;
    Long sessionId;
    Long userId;
    String pdfFilename;
    String csvFilename;
}
