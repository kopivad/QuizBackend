package com.kopivad.quizzes.controller;

import com.kopivad.quizzes.dto.QuizAnswerDto;
import com.kopivad.quizzes.service.QuizAnswerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController()
@RequestMapping("api/v1/quiz/answer")
@RequiredArgsConstructor
public class QuizAnswerController {
    private final QuizAnswerService quizAnswerService;

    @PostMapping
    public ResponseEntity<Void> save(@RequestBody QuizAnswerDto quizAnswerDto) {
        if (quizAnswerService.save(quizAnswerDto)) {
            return ResponseEntity.status(HttpStatus.CREATED).build();
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }
}
