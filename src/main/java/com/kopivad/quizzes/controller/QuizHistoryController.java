package com.kopivad.quizzes.controller;

import com.kopivad.quizzes.domain.QuizHistory;
import com.kopivad.quizzes.service.QuizHistoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

import static org.apache.commons.lang3.math.NumberUtils.INTEGER_ZERO;

@RestController()
@RequestMapping("api/v1/quiz/history")
@RequiredArgsConstructor
public class QuizHistoryController {
    private final QuizHistoryService quizHistoryService;

    @PostMapping("create")
    public ResponseEntity<Long> createHistory(@RequestBody long sessionId) {
        long historyId = quizHistoryService.createHistory(sessionId);
        if (historyId > INTEGER_ZERO) {
            return ResponseEntity.status(HttpStatus.CREATED).body(historyId);
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

    @GetMapping("{id}")
    public ResponseEntity<QuizHistory> getById(@PathVariable long id) {
        return ResponseEntity.of(quizHistoryService.getById(id));
    }

    @GetMapping("pdf/{id}")
    public ResponseEntity<Resource> downloadPdf(@PathVariable long id) {
        Optional<Resource> pdf = quizHistoryService.getPDF(id);
        if (pdf.isPresent()) {
            if (pdf.get().exists()) {
                return ResponseEntity.of(pdf);
            }
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();

    }

    @GetMapping("csv/{id}")
    public ResponseEntity<Resource> downloadCsv(@PathVariable(name = "id") long id) {
        Optional<Resource> csv = quizHistoryService.getCSV(id);
        if (csv.isPresent()) {
            if (csv.get().exists()) {
                return ResponseEntity.of(csv);
            }
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    @GetMapping("all")
    public ResponseEntity<List<QuizHistory>> getAll() {
        return ResponseEntity.ok(quizHistoryService.getAll());
    }
}
