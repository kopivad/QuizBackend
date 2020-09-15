package com.kopivad.quizzes.service.impl;

import com.kopivad.quizzes.domain.Question;
import com.kopivad.quizzes.dto.AnswerDto;
import com.kopivad.quizzes.dto.FullQuestionDto;
import com.kopivad.quizzes.dto.QuestionDto;
import com.kopivad.quizzes.repository.QuestionRepository;
import com.kopivad.quizzes.service.AnswerService;
import com.kopivad.quizzes.service.QuestionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.apache.commons.lang3.math.NumberUtils.INTEGER_ONE;

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
    public Optional<FullQuestionDto> getById(Long id) {
        Optional<Question> question = questionRepository.findById(id);
        return question.map(resultQuestion -> new FullQuestionDto(resultQuestion, answerService.getByQuestionId(id)));
    }

    @Override
    public boolean save(QuestionDto dto) {
        long id = questionRepository.save(dto);
        List<AnswerDto> answers = fillQuestionIdForAllAnswerDtos(dto.getAnswers(), id);
        return answerService.saveAll(answers);
    }

    private List<AnswerDto> fillQuestionIdForAllAnswerDtos(List<AnswerDto> dtos, Long id) {
        return dtos
                .stream()
                .map(dto -> new AnswerDto(dto.getBody(), dto.isRight(), id))
                .collect(Collectors.toUnmodifiableList());
    }

    @Override
    public boolean update(Question question) {
        int affectedRows = questionRepository.update(question);
        return affectedRows == INTEGER_ONE;
    }

    @Override
    public boolean delete(Long id) {
        int affectedRows = questionRepository.delete(id);
        return affectedRows == INTEGER_ONE;
    }

    @Override
    public List<FullQuestionDto> getFullByQuizId(Long id) {
        List<Question> questions = questionRepository.findByQuizId(id);
        return questions
                .stream()
                .map(question -> new FullQuestionDto(question, answerService.getByQuestionId(question.getId())))
                .collect(Collectors.toUnmodifiableList());
    }

    @Override
    public boolean saveAll(List<QuestionDto> dtos) {
        return questionRepository.saveAll(dtos) == dtos.size();
    }
}
