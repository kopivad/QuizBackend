package com.kopivad.quizzes.controller;

import com.kopivad.quizzes.domain.Quiz;
import com.kopivad.quizzes.dto.QuizDto;
import com.kopivad.quizzes.service.QuizService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/quiz")
@RequiredArgsConstructor
public class QuizController {
    private final QuizService quizService;

    @GetMapping("all")
    public List<QuizDto> getAll() {
        return quizService.getAll();
    }

    @GetMapping("{id}")
    public Quiz getById(@PathVariable(name = "id") Long id) {
        return quizService.getById(id);
    }

    @PostMapping()
    public long save(@RequestBody QuizDto quizDto) {
        return quizService.save(quizDto);
    }

    @PutMapping()
    public boolean update(@RequestBody QuizDto quizDto) {
        return quizService.update(quizDto);
    }

    @DeleteMapping("{id}")
    public boolean delete(@PathVariable Long id) {
        return quizService.delete(id);
    }
}
