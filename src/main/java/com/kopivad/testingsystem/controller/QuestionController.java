//package com.kopivad.testingsystem.controller;
//
//import com.kopivad.testingsystem.domain.Question;
//import lombok.AllArgsConstructor;
//import org.springframework.beans.BeanUtils;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.List;
//
//@RestController
//@RequestMapping("/api/question")
//@AllArgsConstructor
//public class QuestionController {
//    private final QuestionRepository questionRepository;
//
//    @GetMapping("/all")
//    public List<Question> getAll() {
//        return questionRepository.findAll();
//    }
//
//    @GetMapping("{id}")
//    public Question getById(@PathVariable(name = "id") Question question) {
//        return question;
//    }
//
//    @PostMapping()
//    public Question add(@RequestBody Question question) {
//        return questionRepository.save(question);
//    }
//
//    @PutMapping("{id}")
//    public Question update(@PathVariable("id") Question questionFromDB, @RequestBody Question question) {
//        BeanUtils.copyProperties(question, questionFromDB, "id");
//        return questionRepository.save(questionFromDB);
//    }
//
//    @DeleteMapping("{id}")
//    public void delete(@PathVariable Question question) {
//        questionRepository.delete(question);
//    }
//}
