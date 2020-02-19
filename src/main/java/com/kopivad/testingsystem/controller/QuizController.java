package com.kopivad.testingsystem.controller;

import com.kopivad.testingsystem.domain.Quiz;
import com.kopivad.testingsystem.repository.data.QuizRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/quiz")
@AllArgsConstructor
public class QuizController {
    private final QuizRepository quizRepository;

    @GetMapping("/all")
    public List<Quiz> getAll() {
        return quizRepository.findAll();
    }

    @GetMapping("{id}")
    public Quiz getById(@PathVariable(name = "id") Quiz quiz) {
        return quiz;
    }

    @PostMapping()
    public Quiz add(@RequestBody Quiz quiz) {
        return quizRepository.save(quiz);
    }

    @PutMapping("{id}")
    public Quiz update(@PathVariable("id") Quiz quizFromDB, @RequestBody Quiz quiz) {
        BeanUtils.copyProperties(quiz, quizFromDB, "id");
        return quizRepository.save(quizFromDB);
    }

    @DeleteMapping("{id}")
    public void delete(@PathVariable Quiz quiz) {
        quizRepository.delete(quiz);
    }
}
