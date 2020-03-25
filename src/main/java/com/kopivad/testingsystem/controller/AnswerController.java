package com.kopivad.testingsystem.controller;

import com.kopivad.testingsystem.domain.Answer;
import com.kopivad.testingsystem.dto.AnswerDto;
import com.kopivad.testingsystem.dto.util.DtoUtils;
import com.kopivad.testingsystem.service.AnswerService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/answer")
@AllArgsConstructor
public class AnswerController {
    private final AnswerService answerService;

    @GetMapping("/all")
    public List<Answer> getAll() {
        return answerService.getAll();
    }

    @GetMapping("{id}")
    public Answer getById(@PathVariable(name = "id") Long id) {
        return answerService.getById(id);
    }

    @PostMapping()
    public Answer add(@RequestBody AnswerDto answerDto) {
        return answerService.save(DtoUtils.getAnswerFromDto(answerDto));
    }

    @PutMapping("{id}")
    public Answer update(@PathVariable("id") Long id, @RequestBody AnswerDto answerDto) {
        return answerService.update(id, DtoUtils.getAnswerFromDto(answerDto));
    }

    @DeleteMapping("{id}")
    public void delete(@PathVariable Long id) {
        answerService.delete(id);
    }

}
