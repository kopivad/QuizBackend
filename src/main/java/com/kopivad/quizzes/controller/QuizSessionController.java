package com.kopivad.quizzes.controller;

import com.kopivad.quizzes.dto.QuizSessionDto;
import com.kopivad.quizzes.service.QuizSessionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController()
@RequestMapping("api/v1/quiz/session")
@RequiredArgsConstructor
public class QuizSessionController {
    private final QuizSessionService quizSessionService;

    @PostMapping
    public ResponseEntity<Long> save(@RequestBody QuizSessionDto dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(quizSessionService.save(dto));
    }
}
