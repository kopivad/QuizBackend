package com.kopivad.quizzes.service.impl;

import com.kopivad.quizzes.domain.Answer;
import com.kopivad.quizzes.domain.Question;
import com.kopivad.quizzes.dto.QuestionDto;
import com.kopivad.quizzes.mapper.QuestionMapper;
import com.kopivad.quizzes.repository.QuestionRepository;
import com.kopivad.quizzes.service.AnswerService;
import com.kopivad.quizzes.service.QuestionService;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class QuestionServiceImpl implements QuestionService {
    private final QuestionRepository questionRepository;
    private final AnswerService answerService;
    private final QuestionMapper questionMapper;

    @Override
    public List<QuestionDto> getAll() {
        List<Question> questions = questionRepository.findAll();
        return questions
                .stream()
                .map(questionMapper::toDto)
                .collect(Collectors.toUnmodifiableList());
    }

    @Override
    public Question getById(Long id) {
        Question question = questionRepository.findById(id);
        List<Answer> answers = answerService.getByQuestionId(id);
        return question.toBuilder().answers(answers).build();
    }

    @Override
    public long save(QuestionDto questionDto) {
        Question question = questionMapper.toEntity(questionDto);
        long id = questionRepository.save(question);
        if (ObjectUtils.isNotEmpty(question.getAnswers())) {
            List<Answer> answers = question.getAnswers();
            List<Answer> answersWithQuestion = setQuestionForAllAnswers(id, answers);
            answerService.saveAll(answersWithQuestion);
        }
        return id;
    }

    private List<Answer> setQuestionForAllAnswers(long id, List<Answer> answers) {
        return answers
                .stream()
                .map(answer -> answer.toBuilder().question(Question.builder().id(id).build()).build())
                .collect(Collectors.toUnmodifiableList());
    }

    @Override
    public boolean update(QuestionDto questionDto) {
        Question question = questionMapper.toEntity(questionDto);
        return questionRepository.update(question);
    }

    @Override
    public boolean delete(Long id) {
        return questionRepository.delete(id);
    }

    @Override
    public List<Question> getByQuizId(Long id) {
        List<Question> questions = questionRepository.findByQuizId(id);
        return questions
                .stream()
                .map(question -> {
                    List<Answer> answers = answerService.getByQuestionId(question.getId());
                    return question.toBuilder().answers(answers).build();
                })
                .collect(Collectors.toUnmodifiableList());
    }

    @Override
    public void saveAll(List<QuestionDto> dtos) {
        dtos.forEach(this::save);
    }
}
