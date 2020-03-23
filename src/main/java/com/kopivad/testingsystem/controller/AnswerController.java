//package com.kopivad.testingsystem.controller;
//
//import com.kopivad.testingsystem.domain.Answer;
//import lombok.AllArgsConstructor;
//import org.springframework.beans.BeanUtils;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.List;
//
//@RestController
//@RequestMapping("/api/answer")
//@AllArgsConstructor
//public class AnswerController {
//    private final AnswerRepository answerRepository;
//
//    @GetMapping("/all")
//    public List<Answer> getAll() {
//        return answerRepository.findAll();
//    }
//
//    @GetMapping("{id}")
//    public Answer getById(@PathVariable(name = "id") Answer Answer) {
//        return Answer;
//    }
//
//    @PostMapping()
//    public Answer add(@RequestBody Answer Answer) {
//        return answerRepository.save(Answer);
//    }
//
//    @PutMapping("{id}")
//    public Answer update(@PathVariable("id") Answer AnswerFromDB, @RequestBody Answer Answer) {
//        BeanUtils.copyProperties(Answer, AnswerFromDB, "id");
//        return answerRepository.save(AnswerFromDB);
//    }
//
//    @DeleteMapping("{id}")
//    public void delete(@PathVariable Answer Answer) {
//        answerRepository.delete(Answer);
//    }
//
//}
