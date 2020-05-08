package com.kopivad.quizzes.service.impl;

import com.kopivad.quizzes.domain.Quiz;
import com.kopivad.quizzes.repository.utils.QuizUtils;
import com.kopivad.quizzes.service.QuizService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

@ExtendWith(SpringExtension.class)
@SpringBootTest
class QuizServiceImplUnitTest {
    @Autowired
    private QuizService quizService;

    @Test
    void saveFullWithCorrectData() {
        Quiz quizForSave = QuizUtils.generateFullQuiz();
        Quiz savedQuiz = quizService.saveFull(quizForSave);
        assertThat(quizForSave.getId(), not(equalTo(savedQuiz.getId())));
        assertThat(quizForSave.getQuestions().size(), equalTo(savedQuiz.getQuestions().size()));
        assertThat(quizForSave.getAuthor(), equalTo(savedQuiz.getAuthor()));
    }

    @Test
    void saveFullWithNullQuestionsAndAnswers() {
        Quiz quizForSave = QuizUtils.generateQuiz();
        Quiz savedQuiz = quizService.saveFull(quizForSave);
        assertThat(quizForSave.getId(), not(equalTo(savedQuiz.getId())));
        assertThat(quizForSave.getTitle(), equalTo(savedQuiz.getTitle()));
        assertThat(quizForSave.getDescription(), equalTo(savedQuiz.getDescription()));
    }

    @Test
    void getFull() {
        Quiz quizForSave = QuizUtils.generateFullQuiz();
        Quiz savedQuiz = quizService.saveFull(quizForSave);
        Quiz quizFromBD = quizService.getFullById(savedQuiz.getId());
        assertThat(quizFromBD, notNullValue());
        assertThat(quizFromBD.getId(), equalTo(savedQuiz.getId()));
        assertEquals(quizFromBD.getQuestions(), savedQuiz.getQuestions());
    }
}