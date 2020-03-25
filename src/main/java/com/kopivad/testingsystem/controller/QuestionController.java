package com.kopivad.testingsystem.controller;

import com.kopivad.testingsystem.domain.Question;
import com.kopivad.testingsystem.dto.QuestionDto;
import com.kopivad.testingsystem.dto.util.DtoUtils;
import com.kopivad.testingsystem.service.QuestionService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/question")
@AllArgsConstructor
public class QuestionController {
    private final QuestionService questionService;

    @GetMapping("/all")
    public List<Question> getAll() {
        return questionService.getAll();
    }

    @GetMapping("{id}")
    public Question getById(@PathVariable(name = "id") Long id) {
        return questionService.getById(id);
    }

    @PostMapping()
    public Question add(@RequestBody QuestionDto questionDto) {
        return questionService.save(DtoUtils.getQuestionFromDto(questionDto));
    }

    @PutMapping("{id}")
    public Question update(@PathVariable("id") Long id, @RequestBody QuestionDto questionDto) {
        return questionService.update(id, DtoUtils.getQuestionFromDto(questionDto));
    }

    @DeleteMapping("{id}")
    public void delete(@PathVariable Long id) {
        questionService.delete(id);
    }
}
