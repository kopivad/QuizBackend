package com.kopivad.quizzes.controller;

import com.kopivad.quizzes.domain.Question;
import com.kopivad.quizzes.form.QuestionFrom;
import com.kopivad.quizzes.service.QuestionService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/question")
@RequiredArgsConstructor
public class QuestionController {
    private final QuestionService questionService;

    @GetMapping("all")
    public List<Question> getAll() {
        return questionService.getAll();
    }

    @GetMapping("{id}")
    public Question getById(@PathVariable(name = "id") Long id) {
        return questionService.getById(id);
    }

    @PostMapping()
    public Question add(@RequestBody QuestionFrom questionFrom) {
        return questionService.save(questionFrom.toQuestion());
    }

    @PutMapping("{id}")
    public Question update(@PathVariable("id") Long id, @RequestBody QuestionFrom questionFrom) {
        return questionService.update(id, questionFrom.toQuestion());
    }

    @DeleteMapping("{id}")
    public void delete(@PathVariable Long id) {
        questionService.delete(id);
    }
}
