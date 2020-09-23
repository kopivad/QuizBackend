package com.kopivad.quizzes.controller;

import com.kopivad.quizzes.domain.QuizHistory;
import com.kopivad.quizzes.service.QuizHistoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
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

    @GetMapping(value = "pdf/{id}", produces = MediaType.APPLICATION_PDF_VALUE)
    public ResponseEntity<Resource> downloadPdf(@PathVariable long id) {
        try {
            return quizHistoryService
                    .getPdfResource(id)
                    .map(resource -> ResponseEntity
                            .ok()
                            .header(HttpHeaders.CONTENT_DISPOSITION, String.format("filename=%s", resource.getFilename()))
                            .body(resource))
                    .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());

        } catch (FileNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @GetMapping(value = "csv/{id}", produces = "text/csv")
    public ResponseEntity<Resource> downloadCsv(@PathVariable long id) {
        try {
            return quizHistoryService
                    .getCsvResource(id)
                    .map(resource -> ResponseEntity
                            .ok()
                            .header(HttpHeaders.CONTENT_DISPOSITION, String.format("filename=%s", resource.getFilename()))
                            .body(resource))
                    .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());

        } catch (FileNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @GetMapping("all")
    public ResponseEntity<List<QuizHistory>> getAll() {
        return ResponseEntity.ok(quizHistoryService.getAll());
    }
}
