package com.kopivad.quizzes.controller;

import com.kopivad.quizzes.domain.QuizHistory;
import com.kopivad.quizzes.service.QuizHistoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("pdf/{id}")
    public ResponseEntity<byte[]> downloadPdf(@PathVariable long id) {
        return ResponseEntity.of(quizHistoryService.getPDF(id));
    }

    @GetMapping("csv/{id}")
    public ResponseEntity<byte[]> downloadCsv(@PathVariable long id) {
        return ResponseEntity.of(quizHistoryService.getCSV(id));
    }

    @GetMapping("all")
    public ResponseEntity<List<QuizHistory>> getAll() {
        return ResponseEntity.ok(quizHistoryService.getAll());
    }
}
