package com.kopivad.quizzes.service.impl;

import com.kopivad.quizzes.domain.*;
import com.kopivad.quizzes.dto.QuizHistoryDto;
import com.kopivad.quizzes.exception.FileNotFoundException;
import com.kopivad.quizzes.repository.QuizHistoryRepository;
import com.kopivad.quizzes.service.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@Slf4j
public class QuizHistoryServiceImpl implements QuizHistoryService {
    private final String fileDir;
    private final QuizHistoryRepository quizHistoryRepository;
    private final QuizSessionService quizSessionService;
    private final QuizAnswerService quizAnswerService;
    private final AnswerService answerService;
    private final QuestionService questionService;
    private final EvaluationStepService stepService;

    @Autowired
    public QuizHistoryServiceImpl(
            @Value("#{environment.FILE_DIR}") String fileDir,
            QuizHistoryRepository quizHistoryRepository,
            QuizSessionService quizSessionService,
            QuizAnswerService quizAnswerService,
            AnswerService answerService,
            QuestionService questionService,
            EvaluationStepService stepService
    ) {
        this.fileDir = fileDir;
        this.quizHistoryRepository = quizHistoryRepository;
        this.quizSessionService = quizSessionService;
        this.quizAnswerService = quizAnswerService;
        this.answerService = answerService;
        this.questionService = questionService;
        this.stepService = stepService;
    }

    @Override
    public long save(QuizHistoryDto dto) {
        return quizHistoryRepository.save(dto);
    }

    @Override
    public Optional<Resource> getPdfResource(long id) throws FileNotFoundException {
        Optional<QuizHistory> history = quizHistoryRepository.findById(id);
        return history.map(resultHistory -> {
            String pdfFilename = resultHistory.getPdfFilename();
            Resource resource = new FileSystemResource(fileDir + pdfFilename);
            if (resource.exists()) {
                return resource;
            } else {
                log.error(String.format("PDF file with name %s not found!", pdfFilename));
                throw new FileNotFoundException(String.format("PDF file with name %s not found!", pdfFilename));
            }
        });
    }

    @Override
    public Optional<Resource> getCsvResource(long id) throws FileNotFoundException {
        Optional<QuizHistory> history = quizHistoryRepository.findById(id);
        return history.map(resultHistory -> {
            String csvFilename = resultHistory.getCsvFilename();
            Resource resource = new FileSystemResource(fileDir + csvFilename);
            if (resource.exists()) {
                return resource;
            } else {
                log.error(String.format("CSV file with name %s not found!", csvFilename));
                throw new FileNotFoundException(String.format("CSV file with name %s not found!", csvFilename));
            }
        });
    }

    private void createPDF(String filename, long id) {
        Optional<QuizHistory> history = quizHistoryRepository.findById(id);
        try (PDDocument document = new PDDocument()) {
            PDPage page = new PDPage();
            document.addPage(page);
            try (PDPageContentStream contentStream = new PDPageContentStream(document, page)) {
                contentStream.setFont(PDType1Font.TIMES_ROMAN, 14);
                contentStream.beginText();
                contentStream.showText(history.toString());
                contentStream.endText();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            document.save(fileDir + filename);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void createCSV(String filename, long id) {
        Optional<QuizHistory> history = quizHistoryRepository.findById(id);
        try (
                FileWriter out = new FileWriter(fileDir + filename);
                CSVPrinter printer = new CSVPrinter(out, CSVFormat.DEFAULT)
        ) {
            printer.printRecord(history.toString());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Optional<Long> createHistory(long sessionId) {
        Optional<QuizSession> session = quizSessionService.getById(sessionId);
        return session.map(resultSession -> {
            int total = calculateTotal(quizAnswerService.getAllBySessionId(sessionId));
            String rating = calculateRating(total, session.get().getQuizId());
            String pdfFilename = UUID.randomUUID().toString() + ".pdf";
            String csvFilename = UUID.randomUUID().toString() + ".csv";
            QuizHistoryDto dto = new QuizHistoryDto(total, rating, sessionId, session.get().getUserId(), pdfFilename, csvFilename);
            long id = save(dto);
            createPDF(pdfFilename, id);
            createCSV(csvFilename, id);
            return id;
        });
    }

    @Override
    public Optional<QuizHistory> getById(long id) {
        return quizHistoryRepository.findById(id);
    }

    @Override
    public List<QuizHistory> getAll() {
        return quizHistoryRepository.findAll();
    }

    private String calculateRating(int total, long quizId) {
        List<EvaluationStep> steps = stepService.getByQuizId(quizId);
        return steps
                .stream()
                .filter(step -> step.getMinTotal() <= total && step.getMaxTotal() >= total)
                .map(EvaluationStep::getRating)
                .findFirst()
                .orElse("Lowest rating");
    }

    private int calculateTotal(List<QuizAnswer> quizAnswers) {
        return quizAnswers
                .stream()
                .map(answer -> answerService.getById(answer.getAnswerId()).orElseThrow(IllegalArgumentException::new))
                .filter(Answer::isRight)
                .map(answer ->
                        questionService.getById(answer.getQuestionId())
                                .map(
                                        question -> question.getQuestion().getValue()
                                ).orElseThrow(IllegalArgumentException::new))
                .mapToInt(value -> value).sum();
    }
}
