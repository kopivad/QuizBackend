package com.kopivad.quizzes.service.impl;

import com.kopivad.quizzes.domain.Answer;
import com.kopivad.quizzes.domain.Question;
import com.kopivad.quizzes.dto.FullQuestionDto;
import com.kopivad.quizzes.dto.SaveAnswerDto;
import com.kopivad.quizzes.dto.SaveQuestionDto;
import com.kopivad.quizzes.repository.QuestionRepository;
import com.kopivad.quizzes.service.AnswerService;
import com.kopivad.quizzes.service.QuestionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
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
    public Optional<FullQuestionDto> getById(Long id) {
        Optional<Question> question = questionRepository.findById(id);
        if (question.isPresent()) {
            List<Answer> answers = answerService.getByQuestionId(id);
            FullQuestionDto fullQuestionDto = new FullQuestionDto(question.get(), answers);
            return Optional.of(fullQuestionDto);
        }
        return Optional.empty();
    }

    @Override
    public boolean save(SaveQuestionDto dto) {
        Question question = new Question(1L, dto.getTitle(), dto.getValue(), dto.getType(), dto.getQuizId());
        long id = questionRepository.save(question);
        List<Answer> answers = fillQuestionIdForAllAnswerDtos(dto.getAnswers(), id);
        return answerService.saveAll(answers);
    }

    private List<Answer> fillQuestionIdForAllAnswerDtos(List<SaveAnswerDto> dto, Long id) {
        return dto
                .stream()
                .map(d -> new Answer(1L, d.getBody(), d.isRight(), id))
                .collect(Collectors.toUnmodifiableList());
    }

    @Override
    public boolean update(Question question) {
        return questionRepository.update(question);
    }

    @Override
    public boolean delete(Long id) {
        return questionRepository.delete(id);
    }

    @Override
    public List<FullQuestionDto> getFullByQuizId(Long id) {
        List<Question> questions = questionRepository.findByQuizId(id);
        return questions
                .stream()
                .map(q -> {
                    List<Answer> answers = answerService.getByQuestionId(q.getId());
                    return new FullQuestionDto(q, answers);
                }).collect(Collectors.toUnmodifiableList());
    }

    @Override
    public boolean saveAll(List<SaveQuestionDto> dtos) {
        dtos.forEach(this::save);
        return true;
    }
}
