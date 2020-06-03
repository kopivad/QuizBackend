package com.kopivad.quizzes.service.impl;

import com.kopivad.quizzes.domain.Answer;
import com.kopivad.quizzes.domain.Question;
import com.kopivad.quizzes.form.QuestionForm;
import com.kopivad.quizzes.repository.QuestionRepository;
import com.kopivad.quizzes.service.AnswerService;
import com.kopivad.quizzes.service.QuestionService;
import com.kopivad.quizzes.utils.FormUtils;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class QuestionServiceImpl implements QuestionService {
    private final QuestionRepository questionRepository;
    private final AnswerService answerService;

    @Override
    public List<Question> getAll() {
        return questionRepository.findAll();
    }

    @Override
    public Question getById(Long id) {
        Question questionById = questionRepository.findById(id);
        List<Answer> answers = answerService.getByQuestionId(id);
        return questionById.toBuilder().answers(answers).build();
    }

    @Override
    public Question save(QuestionForm questionForm) {
        Question question = FormUtils.toQuestion(questionForm);
        Question savedQuestion = questionRepository.save(question);
        if (ObjectUtils.isNotEmpty(question.getAnswers())) {
            List<Answer> answers = question.getAnswers();
            List<Answer> answersWithQuestion = setQuestionForAllAnswers(savedQuestion, answers);
            List<Answer> savedAnswers = answerService.saveAll(answersWithQuestion);
            return savedQuestion.toBuilder().answers(savedAnswers).build();
        }
        return savedQuestion;
    }

    private List<Answer> setQuestionForAllAnswers(Question question, List<Answer> answers) {
        return answers
                .stream()
                .map(answer -> answer.toBuilder().question(question).build())
                .collect(Collectors.toUnmodifiableList());
    }

    @Override
    public Question update(Long id, QuestionForm questionForm) {
        Question question = FormUtils.toQuestion(questionForm);
        return questionRepository.update(id, question);
    }

    @Override
    public void delete(Long id) {
        questionRepository.delete(id);
    }

    @Override
    public List<Question> getByQuizId(Long id) {
        return questionRepository.findByQuizId(id);
    }

    @Override
    public List<Question> saveAll(List<Question> questions) {
        return questions
                .stream()
                .map(questionRepository::save)
                .collect(Collectors.toUnmodifiableList());
    }
}
