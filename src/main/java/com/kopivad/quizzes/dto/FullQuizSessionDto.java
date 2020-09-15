package com.kopivad.quizzes.dto;

import com.kopivad.quizzes.domain.QuizAnswer;
import com.kopivad.quizzes.domain.QuizHistory;
import com.kopivad.quizzes.domain.QuizSession;
import lombok.Value;

import java.util.List;

@Value
public class FullQuizSessionDto {
    QuizSession session;
    List<QuizAnswer> results;
    List<QuizHistory> histories;
}
