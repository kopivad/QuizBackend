package com.kopivad.quizzes.dto;

import com.kopivad.quizzes.domain.QuizHistory;
import lombok.Value;

@Value
public class FullQuizHistoryDto {
    QuizHistory history;
}
