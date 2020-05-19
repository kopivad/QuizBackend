//package com.kopivad.quizzes.service.impl;
//
//import com.kopivad.quizzes.domain.Quiz;
//import com.kopivad.quizzes.utils.QuizUtils;
//import com.kopivad.quizzes.service.QuizService;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.test.context.junit.jupiter.SpringExtension;
//
//import static org.hamcrest.Matchers.*;
//import static org.junit.Assert.assertEquals;
//import static org.junit.Assert.assertThat;
//
//@ExtendWith(SpringExtension.class)
//@SpringBootTest
//class QuizServiceImplUnitTest {
//    @Autowired
//    private QuizService quizService;
//
//    @Test
//    void testSaveFullQuizWithCorrectData() {
//        Quiz actualResult = QuizUtils.generateFullQuiz();
//        Quiz expectedResult = quizService.saveFull(actualResult);
//        assertThat(actualResult, not(expectedResult));
//        assertThat(actualResult.getQuestions().size(), equalTo(expectedResult.getQuestions().size()));
//        assertThat(actualResult.getAuthor(), equalTo(expectedResult.getAuthor()));
//    }
//
//    @Test
//    void testSaveFullQuizWithNullQuestionsAndAnswers() {
//        Quiz actualResult = QuizUtils.generateQuiz();
//        Quiz expectedResult = quizService.saveFull(actualResult);
//        assertThat(actualResult, not(expectedResult));
//        assertThat(actualResult.getTitle(), equalTo(expectedResult.getTitle()));
//        assertThat(actualResult.getDescription(), equalTo(expectedResult.getDescription()));
//    }
//
//    @Test
//    void testGetFullQuiz() {
//        Quiz quizForSave = QuizUtils.generateFullQuiz();
//        Quiz expectedResult = quizService.saveFull(quizForSave);
//        Quiz actualResult = quizService.getFullById(expectedResult.getId());
//        assertThat(actualResult, notNullValue());
//        assertThat(actualResult, is(expectedResult));
//        assertEquals(actualResult.getQuestions(), expectedResult.getQuestions());
//    }
//}