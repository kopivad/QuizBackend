package com.kopivad.quizzes.controller;

import com.kopivad.quizzes.domain.Answer;
import com.kopivad.quizzes.dto.AnswerDto;
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
    public List<AnswerDto> getAll() {
        return answerService.getAll();
    }

    @GetMapping("{id}")
    public Answer getById(@PathVariable(name = "id") Long id) {
        return answerService.getById(id);
    }

    @PostMapping()
    public long save(@RequestBody AnswerDto answerDto) {
        return answerService.save(answerDto);
    }

    @PutMapping("{id}")
    public boolean update(@RequestBody AnswerDto answerDto) {
        return answerService.update(answerDto);
    }

    @DeleteMapping("{id}")
    public boolean delete(@PathVariable Long id) {
        return answerService.delete(id);
    }

}
