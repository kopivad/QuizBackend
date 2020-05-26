package com.kopivad.quizzes.controller;

import com.kopivad.quizzes.domain.Quiz;
import com.kopivad.quizzes.form.QuizForm;
import com.kopivad.quizzes.service.QuizService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/quiz")
@RequiredArgsConstructor
public class QuizController {
    private final QuizService quizService;

    @GetMapping("all")
    public List<Quiz> getAll() {
        return quizService.getAll();
    }

    @GetMapping("{id}")
    public Quiz getById(@PathVariable(name = "id") Long id) {
        return quizService.getById(id);
    }

    @PostMapping()
    public Quiz save(@RequestBody QuizForm quizForm) {
        return quizService.save(quizForm);
    }

    @PutMapping("{id}")
    public Quiz update(@PathVariable("id") Long id, @RequestBody QuizForm quizForm) {
        return quizService.update(id, quizForm);
    }

    @DeleteMapping("{id}")
    public void delete(@PathVariable Long id) {
        quizService.delete(id);
    }
}
