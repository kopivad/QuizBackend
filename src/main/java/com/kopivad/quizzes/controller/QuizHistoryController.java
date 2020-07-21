package com.kopivad.quizzes.controller;

import com.kopivad.quizzes.domain.QuizHistory;
import com.kopivad.quizzes.dto.QuizHistoryDto;
import com.kopivad.quizzes.service.QuizHistoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController()
@RequestMapping("api/v1/quiz/history")
@RequiredArgsConstructor
public class QuizHistoryController {
    private final QuizHistoryService quizHistoryService;

    @GetMapping("create/{id}")
    public long createHistory(@PathVariable(name = "id") long sessionId) {
        return quizHistoryService.createHistory(sessionId);
    }

    @GetMapping("{id}")
    public QuizHistory getById(@PathVariable(name = "id") long id) {
        return quizHistoryService.getById(id);
    }

    @GetMapping("pdf/{id}")
    public ResponseEntity<Resource> downloadPdf(@PathVariable(name = "id") long id) {
        Resource pdf = quizHistoryService.getPDF(id);
        if (!pdf.exists()) return ResponseEntity.notFound().build();
        else return ResponseEntity
                .ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + pdf.getFilename() + "\"")
                .contentType(MediaType.APPLICATION_PDF)
                .body(pdf);
    }

    @GetMapping("csv/{id}")
    public ResponseEntity<Resource> downloadCsv(@PathVariable(name = "id") long id) {
        Resource csv = quizHistoryService.getCSV(id);
        if (!csv.exists()) return ResponseEntity.notFound().build();
        else return ResponseEntity
                .ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + csv.getFilename() + "\"")
                .contentType(MediaType.parseMediaType("text/csv;charset=utf-8"))
                .body(csv);
    }

    @GetMapping("all")
    public List<QuizHistoryDto> getAll() {
        return quizHistoryService.getAll();
    }
}
