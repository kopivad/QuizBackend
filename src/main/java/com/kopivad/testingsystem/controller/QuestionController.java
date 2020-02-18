package com.kopivad.testingsystem.controller;

import com.kopivad.testingsystem.form.QuestionForm;
import com.kopivad.testingsystem.domain.Question;
import com.kopivad.testingsystem.domain.Quiz;
import com.kopivad.testingsystem.domain.User;
import com.kopivad.testingsystem.service.QuestionService;
import com.kopivad.testingsystem.service.QuizService;
import com.kopivad.testingsystem.repository.jooq.RepositoryUtils;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Collections;
import java.util.List;

@Controller
@AllArgsConstructor
public class QuestionController {
    private final QuestionService questionService;
    private final QuizService quizService;
    private final RepositoryUtils repositoryUtils;


    @PostMapping(path = "/question/add")
    public String saveQuestion(@AuthenticationPrincipal User user, @Valid QuestionForm form, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("quizzes", quizService.getAllQuizzes());
            model.addAttribute("user", user);
            model.addAllAttributes(ControllerUtils.getErrors(bindingResult));
            return "questionAdd";
        }
        questionService.saveQuestion(form);
        return "redirect:/question/manage";
    }

    @PostMapping(path = "question/edit")
    public String editQuiz(@AuthenticationPrincipal User user, @Valid QuestionForm form, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("question", questionService.getQuestionById(form.getQuestionId()));
            model.addAttribute("quizzes", quizService.getAllQuizzes());
            model.addAttribute("questionForm", form);
            model.addAllAttributes(ControllerUtils.getErrors(bindingResult));
            model.addAttribute("user", user);
            return "questionEdit";
        }

        questionService.updateQuestion(form);
        return "redirect:/question/manage";
    }

    @GetMapping(path = "/question/delete")
    @PreAuthorize("hasAuthority('ADMIN')")
    public String deleteQuestion(@RequestParam(name = "id") Long questionId) {
        questionService.deleteQuestion(questionId);
        return "redirect:/question/manage";
    }

    @GetMapping(path = "quiz/{quizId}/question/{n}")
    public String getQuestionByIdAndQuizId(@PathVariable(name = "quizId") Long quizId,
                                           @PathVariable(name = "n") Integer n,
                                           @ModelAttribute(name = "session") Long sessionId,
                                           Model model,
                                           @AuthenticationPrincipal User user
    ) {

        Pageable pageable = PageRequest.of(n - 1, 1);
        Quiz currentQuiz = quizService.getQuizById(quizId);
        Page<Question> question = questionService.getQuestionByQuizId(quizId, pageable);
        Collections.shuffle(question.getContent().get(0).getAnswers());
        model.addAttribute("quiz", currentQuiz);
        model.addAttribute("question", question.getContent().get(0));
        model.addAttribute("questionNumber", question.getNumber());
        model.addAttribute("questionTotalPages", question.getTotalPages());
        model.addAttribute("sessionId", sessionId);
        model.addAttribute("user", user);
        return "question";
    }


    @GetMapping(path = "question/add")
    public String getAddQuestionPage(Model model, @AuthenticationPrincipal User user, QuestionForm questionForm) {
        List<Quiz> allQuizzes = quizService.getAllQuizzes();
        model.addAttribute("quizzes", allQuizzes);
        model.addAttribute("questionForm", questionForm);
        model.addAttribute("user", user);
        return "questionAdd";
    }
    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping(path = "/question/manage/{id}")
    public String getAllQuestionsManageByQuizIdPage(@PathVariable(name = "id") Long id,  Model model, @AuthenticationPrincipal User user) {
        List<Question> allQuestions = questionService.getQuestionByQuizId(id);
        model.addAttribute("questions", allQuestions);
        model.addAttribute("user", user);
        return "questionManage";
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping(path = "/question/manage")
    public String getQuestionManagePage(Model model, @AuthenticationPrincipal User user) {
        List<Question> allQuestions = questionService.getAllQuestions();
        model.addAttribute("questions", allQuestions);
        model.addAttribute("user", user);
        return "questionManage";
    }

    @GetMapping(path = "/question/edit/{id}")
    public String getEditQuestionPage(@PathVariable(name = "id") Long id, Model model, QuestionForm questionForm, @AuthenticationPrincipal User user) {
        model.addAttribute("question", questionService.getQuestionById(id));
        model.addAttribute("quizzes", quizService.getAllQuizzes());
        model.addAttribute("questionForm", questionForm);
        model.addAttribute("user", user);
        return "questionEdit";
    }

}
