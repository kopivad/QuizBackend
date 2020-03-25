package com.kopivad.quizzes.controller;

import com.kopivad.quizzes.domain.Quiz;
import com.kopivad.quizzes.dto.QuizDto;
import com.kopivad.quizzes.dto.util.DtoUtils;
import com.kopivad.quizzes.service.QuizService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/quiz")
@AllArgsConstructor
public class QuizController {
    private final QuizService quizService;

    @GetMapping("/all")
    public List<Quiz> getAll() {
        return quizService.getAll();
    }

    @GetMapping("{id}")
    public Quiz getById(@PathVariable(name = "id") Long id) {
        return quizService.getById(id);
    }

    @PostMapping()
    public Quiz add(@RequestBody QuizDto quizDto) {
        return quizService.save(DtoUtils.getQuizFromDto(quizDto));
    }

    @PutMapping("{id}")
    public Quiz update(@PathVariable("id") Long id, @RequestBody QuizDto quizDto) {
        return quizService.update(id, DtoUtils.getQuizFromDto(quizDto));
    }

    @DeleteMapping("{id}")
    public void delete(@PathVariable Long id) {
        quizService.delete(id);
    }
}
