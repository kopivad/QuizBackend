package com.kopivad.quizzes.controller;

import com.kopivad.quizzes.domain.Answer;
import com.kopivad.quizzes.dto.AnswerDto;
import com.kopivad.quizzes.service.AnswerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/answer")
@RequiredArgsConstructor
public class AnswerController {
    private final AnswerService answerService;

    @GetMapping("all")
    public ResponseEntity<List<Answer>> getAll() {
        return ResponseEntity.ok(answerService.getAll());
    }

    @GetMapping("{id}")
    public ResponseEntity<Answer> getById(@PathVariable Long id) {
        return ResponseEntity.of(answerService.getById(id));
    }

    @PostMapping
    public ResponseEntity<Void> save(@RequestBody AnswerDto dto) {
        if (answerService.save(dto)) {
            return ResponseEntity.status(HttpStatus.CREATED).build();
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @PatchMapping
    public ResponseEntity<Void> update(@RequestBody Answer answer) {
        if (answerService.update(answer)) {
            return ResponseEntity.status(HttpStatus.OK).build();
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        if (answerService.delete(id)) {
            return ResponseEntity.status(HttpStatus.OK).build();
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }
}
