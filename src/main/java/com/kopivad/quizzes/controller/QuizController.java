package com.kopivad.quizzes.controller;

import com.kopivad.quizzes.domain.Quiz;
import com.kopivad.quizzes.dto.FullQuizDto;
import com.kopivad.quizzes.dto.QuizDto;
import com.kopivad.quizzes.service.QuizService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/quiz")
@RequiredArgsConstructor
public class QuizController {
    private final QuizService quizService;

    @GetMapping("all")
    public ResponseEntity<List<Quiz>> getAll() {
        return ResponseEntity.ok(quizService.getAll());
    }

    @GetMapping("{id}")
    public ResponseEntity<FullQuizDto> getById(@PathVariable Long id) {
        return ResponseEntity.of(quizService.getById(id));
    }

    @GetMapping
    public ResponseEntity<List<Quiz>> getByTitleStartWith(String title) {
        return ResponseEntity.ok(quizService.getByTitleStartsWith(title));
    }

    @PostMapping
    public ResponseEntity<Void> save(@RequestBody QuizDto dto) {
        if (quizService.save(dto)) {
            return ResponseEntity.status(HttpStatus.CREATED).build();
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @PatchMapping
    public ResponseEntity<Void> update(@RequestBody Quiz quiz) {
        if (quizService.update(quiz)) {
            return ResponseEntity.status(HttpStatus.OK).build();
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        if (quizService.delete(id)) {
            return ResponseEntity.status(HttpStatus.OK).build();
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }
}
