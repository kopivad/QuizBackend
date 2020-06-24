package com.kopivad.quizzes.controller;

import com.kopivad.quizzes.domain.Question;
import com.kopivad.quizzes.service.QuestionService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/question")
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
    public Question add(@RequestBody Question question) {
        return questionService.save(question);
    }

    @PutMapping("{id}")
    public Question update(@PathVariable("id") Long id, @RequestBody Question question) {
        return questionService.update(id, question);
    }

    @DeleteMapping("{id}")
    public void delete(@PathVariable Long id) {
        questionService.delete(id);
    }
}
