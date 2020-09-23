package com.kopivad.quizzes.controller;

import com.kopivad.quizzes.domain.QuizHistory;
import com.kopivad.quizzes.service.QuizHistoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.FileNotFoundException;
import java.util.List;

@RestController()
@RequestMapping("api/v1/quiz/history")
@RequiredArgsConstructor
public class QuizHistoryController {
    private final QuizHistoryService quizHistoryService;

    @PostMapping("create")
    public ResponseEntity<Long> createHistory(@RequestBody long sessionId) {
        return ResponseEntity.of(quizHistoryService.createHistory(sessionId));
    }

    @GetMapping("{id}")
    public ResponseEntity<QuizHistory> getById(@PathVariable long id) {
        return ResponseEntity.of(quizHistoryService.getById(id));
    }

    @GetMapping(value = "pdf/{id}", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    public ResponseEntity<byte[]> downloadPdf(@PathVariable long id) {
        try {
            return ResponseEntity.of(quizHistoryService.getPDF(id));
        } catch (FileNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @GetMapping(value = "csv/{id}", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    public ResponseEntity<byte[]> downloadCsv(@PathVariable long id) {
        try {
            return ResponseEntity.of(quizHistoryService.getCSV(id));
        } catch (FileNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @GetMapping("all")
    public ResponseEntity<List<QuizHistory>> getAll() {
        return ResponseEntity.ok(quizHistoryService.getAll());
    }
}
