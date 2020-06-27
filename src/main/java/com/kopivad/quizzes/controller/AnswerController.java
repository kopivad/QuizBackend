package com.kopivad.quizzes.controller;

import com.kopivad.quizzes.domain.Answer;
import com.kopivad.quizzes.service.AnswerService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/answer")
@RequiredArgsConstructor
public class AnswerController {
    private final AnswerService answerService;

    @GetMapping("all")
    public List<Answer> getAll() {
        return answerService.getAll();
    }

    @GetMapping("{id}")
    public Answer getById(@PathVariable(name = "id") Long id) {
        return answerService.getById(id);
    }

    @PostMapping()
    public Answer add(@RequestBody Answer answer) {
        return answerService.save(answer);
    }

    @PutMapping("{id}")
    public Answer update(@PathVariable("id") Long id, @RequestBody Answer answer) {
        return answerService.update(id, answer);
    }

    @DeleteMapping("{id}")
    public void delete(@PathVariable Long id) {
        answerService.delete(id);
    }

}
