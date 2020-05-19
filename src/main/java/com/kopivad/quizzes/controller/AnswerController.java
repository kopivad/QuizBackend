package com.kopivad.quizzes.controller;

import com.kopivad.quizzes.domain.Answer;
import com.kopivad.quizzes.form.AnswerForm;
import com.kopivad.quizzes.service.AnswerService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/answer")
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
    public Answer add(@RequestBody AnswerForm answerForm) {
        return answerService.save(answerForm.toAnswer());
    }

    @PutMapping("{id}")
    public Answer update(@PathVariable("id") Long id, @RequestBody AnswerForm answerForm) {
        return answerService.update(id, answerForm.toAnswer());
    }

    @DeleteMapping("{id}")
    public void delete(@PathVariable Long id) {
        answerService.delete(id);
    }

}
