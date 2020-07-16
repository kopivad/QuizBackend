package com.kopivad.quizzes.controller;

import com.kopivad.quizzes.domain.Question;
import com.kopivad.quizzes.dto.QuestionDto;
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
    public List<QuestionDto> getAll() {
        return questionService.getAll();
    }

    @GetMapping("{id}")
    public Question getById(@PathVariable(name = "id") Long id) {
        return questionService.getById(id);
    }

    @PostMapping()
    public long save(@RequestBody QuestionDto questionDto) {
        return questionService.save(questionDto);
    }

    @PutMapping()
    public boolean update(@RequestBody QuestionDto questionDto) {
        return questionService.update(questionDto);
    }

    @DeleteMapping("{id}")
    public boolean delete(@PathVariable Long id) {
        return questionService.delete(id);
    }
}
